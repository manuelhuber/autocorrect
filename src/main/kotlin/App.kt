import rx.observables.SwingObservable
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class App : JPanel(GridBagLayout()) {
    private var textField: JTextField = JTextField(20)
    private var textArea: JTextArea = JTextArea(5, 20)
    private var autoCorrect: Autocorrect = Autocorrect()

    init {
        initGui()
    }

    private fun initGui() {
        textArea.isEditable = false

        val constraints = GridBagConstraints()
        constraints.gridwidth = GridBagConstraints.REMAINDER

        constraints.fill = GridBagConstraints.HORIZONTAL
        add(textField, constraints)

        constraints.fill = GridBagConstraints.BOTH
        constraints.weightx = 1.0
        constraints.weighty = 1.0

        add(JScrollPane(textArea), constraints)

        SwingObservable.fromKeyEvents(textField).subscribe { showCorrections() }
    }

    private fun showCorrections() {
        textArea.text = autoCorrect.topThreeSuggestions(textField.text).map { (first) -> first }.toString()
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
