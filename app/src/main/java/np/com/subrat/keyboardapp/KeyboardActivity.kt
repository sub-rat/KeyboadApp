package np.com.subrat.keyboardapp

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class KeyboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }
}