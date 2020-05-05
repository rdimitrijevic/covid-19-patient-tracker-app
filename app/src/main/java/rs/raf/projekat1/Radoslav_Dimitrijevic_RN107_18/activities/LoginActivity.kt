package rs.raf.projekat1.Radoslav_Dimitrijevic_RN107_18.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_login.*
import rs.raf.projekat1.Radoslav_Dimitrijevic_RN107_18.R
import rs.raf.projekat1.Radoslav_Dimitrijevic_RN107_18.viewmodels.LoginViewModel
import timber.log.Timber

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val viewModel: LoginViewModel = LoginViewModel()

    companion object {
        val IME: String = "ime"
        val PREZIME: String = "ime"
        val IME_BOLNICE: String = "ime"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        initETListeners()

        btn_login.setOnClickListener {
            val ime: String? = viewModel.getIme().value
            val prezime: String? = viewModel.getPrezime().value
            val imeBolnice: String? = viewModel.getImeBolnice().value
            val pin: String? = viewModel.getPin().value

            if ( !ime.isNullOrEmpty()
                && !prezime.isNullOrEmpty()
                && !imeBolnice.isNullOrEmpty()
                && !pin.isNullOrEmpty() ) {

                Timber.e(pin ?: "nema stringa")
                Timber.e(R.string.PIN.toString())

                if( pin.trim() != "1234" ) {

                    val msg = Toast.makeText(this, getString(R.string.login_pin_errmsg), Toast.LENGTH_SHORT)
                    msg.show()
                } else {
                    val sharedResources = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                    val editor = sharedResources.edit()

                    editor.putString(IME, ime)
                    editor.putString(PREZIME, prezime)
                    editor.putString(IME_BOLNICE, imeBolnice)
                    editor.commit()

                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }

            } else {
                val msg = Toast.makeText(this, getString(R.string.login_fields_errmsg), Toast.LENGTH_SHORT)
                msg.show()
            }
        }
    }

    private fun initETListeners() {
        tf_login_ime.doAfterTextChanged {
            viewModel.setIme(it.toString())
        }

        tf_login_prezime.doAfterTextChanged {
            viewModel.setPrezime(it.toString())
        }

        tf_login_bolnica.doAfterTextChanged {
            viewModel.setImeBolnice(it.toString())
        }

        tf_login_pin.doAfterTextChanged {
            viewModel.setPin(it.toString())
        }
    }

}
