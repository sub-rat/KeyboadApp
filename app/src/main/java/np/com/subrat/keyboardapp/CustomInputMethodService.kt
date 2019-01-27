package np.com.subrat.keyboardapp

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.os.Vibrator
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import java.security.Key

class CustomInputMethodService : InputMethodService() , KeyboardView.OnKeyboardActionListener{
    lateinit var keyboardView: KeyboardView
    lateinit var keyboard:Keyboard
    lateinit var vibrator: Vibrator
    lateinit var audioManager: AudioManager
    var caps = false

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_layout,null) as KeyboardView
        keyboard = Keyboard(this,R.xml.key_pad)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
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
        keyboardSound(primaryCode)
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

    private fun keyboardSound(keyCode:Int){
        vibrator.vibrate(50)
        when(keyCode){
            32-> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
            Keyboard.KEYCODE_DONE, 10 -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
            Keyboard.KEYCODE_DELETE -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
            else -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        }


    }

}