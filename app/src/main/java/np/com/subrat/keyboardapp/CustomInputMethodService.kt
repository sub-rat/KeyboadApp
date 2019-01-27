package np.com.subrat.keyboardapp

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import java.security.Key

class CustomInputMethodService : InputMethodService() , KeyboardView.OnKeyboardActionListener{
    lateinit var keyboardView: KeyboardView
    lateinit var keyboard:Keyboard
    var caps = false

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_layout,null) as KeyboardView
        keyboard = Keyboard(this,R.xml.key_pad)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }


    override fun swipeRight() {

    }

    override fun onPress(p0: Int) {
      }

    override fun onRelease(p0: Int) {
      }

    override fun swipeLeft() {
     }

    override fun swipeUp() {
      }

    override fun swipeDown() {
     }

    override fun onKey(primaryCode: Int, keyKodes: IntArray?) {
        var inputConnection = currentInputConnection
        inputConnection?.let { inputConnection->
            when(primaryCode){
                Keyboard.KEYCODE_DELETE -> {
                    val selectedText = inputConnection.getSelectedText(0)
                    if (TextUtils.isEmpty(selectedText)){
                        inputConnection.deleteSurroundingText(1,0)
                    }else{
                        inputConnection.commitText("",1)
                    }
                }
                Keyboard.KEYCODE_SHIFT -> {
                    caps = !caps
                    keyboard.setShifted(caps)
                    keyboardView.invalidateAllKeys();


                }
                Keyboard.KEYCODE_DONE -> {
                    inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER))
                }
                else -> {
                    var code: Char = primaryCode.toChar()
                    if (code.isLetter() && caps){
                        code = code.toUpperCase()
                    }
                    inputConnection.commitText(code.toString(),1)
                }
            }
        }

     }

    override fun onText(p0: CharSequence?) {
     }

}