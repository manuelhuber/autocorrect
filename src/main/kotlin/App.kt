import rx.observables.SwingObservable
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*


class App : JPanel(GridBagLayout()) {
    private var textField: JTextField = JTextField(20)
    private var textArea: JTextArea = JTextArea(5, 20)
    private var autoCorrect: Autocomplete = Autocomplete()

    init {
        textArea.isEditable = false
        val scrollPane = JScrollPane(textArea)

        //Add Components to this panel.
        val c = GridBagConstraints()
        c.gridwidth = GridBagConstraints.REMAINDER

        c.fill = GridBagConstraints.HORIZONTAL
        add(textField, c)

        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.weighty = 1.0
        add(scrollPane, c)
        SwingObservable.fromKeyEvents(textField).subscribe { showCorrections() }
    }

    private fun showCorrections() {
        textArea.text = autoCorrect.topThreeSuggestions(textField.text, true).map { (first) -> first }.toString()
    }

    companion object {

        private fun createAndShowGUI() {
            //Create and set up the window.
            val frame = JFrame("TextDemo")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            //Add contents to the window.
            frame.add(App())

            //Display the window.
            frame.pack()
            frame.isVisible = true
        }

        @JvmStatic
        fun main(args: Array<String>) {
            //Schedule a job for the event dispatch thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater { createAndShowGUI() }
        }
    }
}
