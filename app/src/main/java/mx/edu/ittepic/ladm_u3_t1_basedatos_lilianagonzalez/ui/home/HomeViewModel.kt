package mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenido al mapeo de empresas"
    }
    val text: LiveData<String> = _text
}