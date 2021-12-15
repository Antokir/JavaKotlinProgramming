package com.lab

import java.awt.*
import javax.swing.*

fun addTextField(textField: TextField, panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridx = 0
    gbc.gridy = 0
    gbc.gridwidth = 4
    panel.add(textField, gbc)
}

fun addResultField(resultField: TextArea, panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridx = 0
    gbc.gridy = 1
    gbc.gridheight = 2
    panel.add(resultField, gbc)
}

fun addOperators(textField: TextField, panel: JPanel, gbc: GridBagConstraints) {
    val operators = "+-/*"
    var x = 0
    gbc.gridwidth = 1
    gbc.gridheight = 1
    for (char in operators) {
        gbc.gridx = x
        gbc.gridy = 3
        panel.add(JButton(char.toString()).apply {
            addActionListener {
                textField.text += char.toString()
            }
        }, gbc)
        x++
    }
    gbc.gridx = 3
    gbc.gridy = 4
    gbc.gridheight = 2
    panel.add(JButton("(").apply {
        addActionListener {
            textField.text += "("
        }
    }, gbc)
    gbc.gridy = 6
    panel.add(JButton(")").apply {
        addActionListener {
            textField.text += ")"
        }
    }, gbc)
}

fun addNumbers(textField: TextField, panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridheight = 1
    gbc.gridwidth = 1
    for (i in 0..9) {
        gbc.gridx = i % 3
        gbc.gridy = i / 3 + 4
        panel.add(JButton(i.toString()).apply {
            addActionListener {
                textField.text += i.toString()
            }
        }, gbc)
    }
}

fun addDot(textField: TextField, panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridx = 1
    gbc.gridy = 7
    gbc.gridheight = 1
    panel.add(JButton(".").apply {
        addActionListener {
            textField.text += "."
        }
    }, gbc)
}

fun addVariablesInformation(informationField: TextField, listModel: DefaultListModel<String>,
                            panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridx = 4
    gbc.gridy = 3
    gbc.gridheight = 1
    panel.add(JLabel("Print information (for example x = 3):").apply {
        font = Font("Serif", Font.ROMAN_BASELINE, 15)
    }, gbc)

    gbc.gridy = 4
    gbc.gridheight = 2
    panel.add(informationField, gbc)

    gbc.gridx = 4
    gbc.gridy = 6
    gbc.gridheight = 2
    panel.add(JScrollPane(JList(listModel)).apply {
        preferredSize = Dimension(10, 10)
    }, gbc)

    gbc.gridheight = 1
    gbc.gridy = 0
    panel.add(JButton("Add information").apply {
        addActionListener {
            listModel.addElement(informationField.text)
        }
    }, gbc)

    gbc.gridy = 1
    panel.add(JButton("Delete information").apply {
        addActionListener {
            listModel.removeElementAt(0)
        }
    }, gbc)

    gbc.gridy = 2
    panel.add(JButton("Clear all information").apply {
        addActionListener {
            listModel.removeAllElements()
        }
    }, gbc)
}

fun isValue(string: String): Boolean {
    var dots = 0
    for (i in string.indices) {
        if (string[i] == '.') {
            if (i == 0 || dots > 0) {
                return false
            }
            dots++
            continue
        }
        if (string[i] !in '0'..'9') {
            return false
        }
    }
    return true
}

fun addGettingResults(calculator: Calculator, textField: TextField,
                      resultField: TextArea, listModel: DefaultListModel<String>,
                      panel: JPanel, gbc: GridBagConstraints) {
    gbc.gridx = 2
    gbc.gridy = 7
    gbc.gridheight = 1
    panel.add(JButton("=").apply {
        addActionListener {
            val variables = HashMap<String, Double>()
            for (i in 0 until listModel.size()) {
                val string = listModel.elementAt(i)
                val variable = StringBuilder()
                val value = StringBuilder()

                var isVariable = true
                for (j in string.indices) {
                    if (string[j] == ' ') {
                        continue
                    }
                    if (string[j] == '=') {
                        isVariable = false
                        continue
                    }
                    if (isVariable) {
                        variable.append(string[j])
                    } else {
                        value.append(string[j])
                    }
                }
                if (isValue(value.toString())) {
                    variables[variable.toString()] = value.toString().toDouble()
                }
            }
            resultField.text = calculator.GetResults(textField.text, variables)
        }
    }, gbc)
}

fun main() {
    val calculator = CalculatorImpl()
    JFrame().apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Calculator"
        setSize(800, 500)

        add(JPanel().apply {
            layout = GridBagLayout()
            val gbc = GridBagConstraints()
            gbc.weighty = 1.0
            gbc.weightx = 1.0

            gbc.fill = GridBagConstraints.BOTH
            val textField = TextField().apply {
                font = Font("Serif", Font.ROMAN_BASELINE, 20)
            }
            addTextField(textField, this, gbc)

            val resultField = TextArea().apply {
                preferredSize = Dimension(10, 10)
                font = Font("Serif", Font.ROMAN_BASELINE, 15)
                isEditable = false
            }
            addResultField(resultField, this, gbc)

            addOperators(textField, this, gbc)
            addNumbers(textField, this, gbc)
            addDot(textField, this, gbc)

            val informationField = TextField().apply {
                font = Font("Serif", Font.ROMAN_BASELINE, 30)
            }
            val listModel = DefaultListModel<String>()
            addVariablesInformation(informationField, listModel, this, gbc)

            addGettingResults(calculator, textField, resultField, listModel, this, gbc)
        })

        isVisible = true
    }
}