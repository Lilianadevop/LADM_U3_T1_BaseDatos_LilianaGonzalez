package mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.ui.notifications

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.Area
import mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.SubDepartamento
import mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    var idSub = -1
    var desc = ""
    var listIdSub = ArrayList<Int>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.insertarsub.setOnClickListener {
            var sub = SubDepartamento(this.requireContext())
            sub.idEdi = binding.idedificio.text.toString()
            sub.piso = binding.piso.text.toString()
            sub.area_id = idSub
            val res =  sub.inserta()
            if (res){
                Toast.makeText(this.requireContext(),"INFORMACIÓN INSERTADA CORRECTAMENTE", Toast.LENGTH_LONG).show()
                mostrarD()
                binding.idedificio.setText("")
                binding.piso.setText("")

            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("HAY UN ERROR")
                    .setMessage("NO SE INSERTO")
                    .show()
            }
        }

        binding.btnactusub.setOnClickListener {
            var subD = SubDepartamento(this.requireContext())
            subD.idSubD = idSub
            subD.idEdi = binding.idedificio.text.toString()
            subD.piso = binding.piso.text.toString()
            subD.area_id = idSub
            val res =  subD.actualizar()
            if (res){
                Toast.makeText(this.requireContext(),"INFORMACIÓN MODIFICADA CORRECTAMENTE",Toast.LENGTH_LONG).show()
                mostrarD()
                binding.idedificio.setText("")
                binding.piso.setText("")
            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("HAY UN ERROR")
                    .setMessage("NO SE ACTUALIZO")
                    .show()
            }
        }

        return root
    }

    private fun mostrarD(){
        try{
            var listaSub = SubDepartamento(this.requireContext()).mostrar()
            var iEdi = ArrayList<String>()
            listIdSub.clear()
            (0..listaSub.size-1).forEach{
                var sd = listaSub.get(it)
                var dat = "Id edificio ${sd.idEdi}"+"Piso: ${sd.piso}"
                iEdi.add(dat)
                listIdSub.add(sd.idSubD)
            }
            binding.listsub.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,iEdi)
        }catch (e:Error){
            Log.e("ERROR","Mensaje: ${e.message}")
        }
    }

    override fun onStart(){
        super.onStart()
        mostrarD()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}