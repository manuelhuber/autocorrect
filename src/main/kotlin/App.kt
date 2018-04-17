import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


class App : JPanel(GridBagLayout()) {
    private var textField: JTextField = JTextField(100)
    private var textArea: JTextArea = JTextArea(10, 100)
    private var autoCorrect: Autocorrect = Autocorrect(DictionaryLoader())
    private var slow = true

    init {
        initGui()
    }

    private fun initGui() {
        textArea.isEditable = false

        val constraints = GridBagConstraints()
        constraints.gridwidth = GridBagConstraints.REMAINDER

        constraints.fill = GridBagConstraints.HORIZONTAL

        val b = JToggleButton("Use Cache Tree")
        b.addActionListener({ slow = !b.isSelected })

        add(b, constraints)
        add(textField, constraints)

        constraints.fill = GridBagConstraints.BOTH
        constraints.weightx = 1.0
        constraints.weighty = 1.0

        add(JScrollPane(textArea), constraints)
        textField.document.addDocumentListener(object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent?) {
                showCorrections()
            }

            override fun insertUpdate(e: DocumentEvent?) {
                showCorrections()
            }

            override fun removeUpdate(e: DocumentEvent?) {
                showCorrections()
            }
        })
    }

    private fun showCorrections() {
        textArea.text = autoCorrect.getSuggestions(textField.text, 2.0, 3, slow)
                .joinToString("\n")
    }

    companion object {

        private fun createAndShowGUI() {
            val frame = JFrame("Autocorrect")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.add(App())
            frame.pack()
            frame.isVisible = true
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Schedule a job for the event dispatch thread:
            // creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater { createAndShowGUI() }
        }
    }
}
