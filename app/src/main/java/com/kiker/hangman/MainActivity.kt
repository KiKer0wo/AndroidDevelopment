package com.kiker.hangman

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kiker.hangman.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var mistakes: Int = 0
    private var checkIfWin: Boolean = false
    private lateinit var stringArray: Array<String>
    private lateinit var encryptedWord: String
    private lateinit var binding: ActivityMainBinding

    private var mysteryWord: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        stringArray = resources.getStringArray(R.array.words)

        val fab = findViewById<FloatingActionButton>(R.id.newWordButton)
        fab.setOnClickListener {
            generateWord()
        }

        binding.checkLetterButton.setOnClickListener {
            checkLetter()
        }

        binding.checkWordButton.setOnClickListener {
            checkWord()
        }

    }

    private fun getRandomWord(): String {
        val number = (0..stringArray.size).random()
        return stringArray[number]
    }

    private fun updateDisplayWord(wordStr: String) {
        val displayWord = findViewById<TextView>(R.id.displayWord)
        displayWord.text = wordStr
    }

    private fun generateWord() {
        var randomWord = getRandomWord()
        mysteryWord = randomWord
        randomWord = encript(randomWord)
        updateDisplayWord(randomWord)
        mistakes = 0
        updateImg(mistakes)
        checkIfWin = false
    }

    private fun checkWord() {
        val word = findViewById<EditText>(R.id.inputText).text.toString()

        if (word.isEmpty()) {
            Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.inputWord), Snackbar.LENGTH_SHORT).show()
        } else if (word == mysteryWord || checkIfWin) {
            Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.correctWord), Snackbar.LENGTH_SHORT).show()
            updateDisplayWord(mysteryWord)
            checkIfWin = true
        } else if (mistakes > 9) {
            mistakes++
            updateImg(mistakes)
            Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.youLost), Snackbar.LENGTH_SHORT).show()
            updateDisplayWord(mysteryWord)
        } else {

            Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.wrongWord), Snackbar.LENGTH_SHORT).show()
            mistakes++
            updateImg(mistakes)
        }
    }

    private fun checkLetter() {
        val letter = findViewById<EditText>(R.id.inputText).text.toString()

        when {
            checkIfWin -> Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.correctWord), Snackbar.LENGTH_SHORT).show()
            letter.isEmpty() -> {
                Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.insertLetter), Snackbar.LENGTH_SHORT).show()
            }
            letter.length > 1 -> {
                Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.tooMuchLetters), Snackbar.LENGTH_SHORT).show()

            }
            mysteryWord.contains(letter) -> {
                Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.letterFound), Snackbar.LENGTH_SHORT).show()
                for (i in mysteryWord.indices) {
                    if (letter[0] == mysteryWord[i]) {
                        val sb = StringBuilder(encryptedWord).also { it.setCharAt(i, letter[0]) }
                        encryptedWord = sb.toString()
                        updateDisplayWord(encryptedWord)
                    }
                }
            }
            mistakes > 9 -> {
                mistakes++
                updateImg(mistakes)
                Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.youLost), Snackbar.LENGTH_SHORT).show()
                updateDisplayWord(mysteryWord)
            }
            else -> {
                Snackbar.make(findViewById(R.id.mainContainer), getString(R.string.noLetterFound), Snackbar.LENGTH_SHORT).show()
                mistakes++
                updateImg(mistakes)
            }
        }

    }

    private fun encript(word: String): String {
        encryptedWord = word
        for (i in word.indices) {
            val sb = StringBuilder(encryptedWord).also { it.setCharAt(i, '*') }
            encryptedWord = sb.toString()
        }
        return encryptedWord
    }

    private fun updateImg(mistakes: Int) {
        val hangmanImg: ImageView = findViewById(R.id.hangmanImage)
        hangmanImg.setImageResource(assigmentImages(mistakes))
    }

    private fun assigmentImages(value: Int): Int {
        return when (value) {
            0 -> R.drawable.hangman0
            1 -> R.drawable.hangman1
            2 -> R.drawable.hangman2
            3 -> R.drawable.hangman3
            4 -> R.drawable.hangman4
            5 -> R.drawable.hangman5
            6 -> R.drawable.hangman6
            7 -> R.drawable.hangman7
            8 -> R.drawable.hangman8
            9 -> R.drawable.hangman9
            10 -> R.drawable.hangman10
            else -> R.drawable.hangman10
        }
    }

}