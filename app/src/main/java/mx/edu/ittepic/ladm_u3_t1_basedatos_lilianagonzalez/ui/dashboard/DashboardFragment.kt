package mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.ui.dashboard

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
import mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez.databinding.FragmentDashboardBinding

// Este es para Agregar y modificar
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listIdA = ArrayList<Int>()
    var idAr = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarD()
        binding.binsertar.setOnClickListener {
            var area = Area(this.requireContext())
            area.descr = binding.edidescri.text.toString()
            area.divi = binding.edidivi.text.toString()
            area.cant_emple = binding.edicant.text.toString().toInt()
            val res =  area.inserta()
            if (res){
                Toast.makeText(this.requireContext(),"INFORMACIÓN INSERTADA CORRECTAMENTE",Toast.LENGTH_LONG).show()
                mostrarD()
                binding.edidescri.setText("")
                binding.edidivi.setText("")
                binding.edicant.setText("")
            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("HAY UN ERROR")
                    .setMessage("NO SE INSERTO")
                    .show()
            }
        }

        binding.btnactu.setOnClickListener {
            var area = Area(this.requireContext())
            area.area_id = idAr
            area.descr = binding.edidescri.text.toString()
            area.divi = binding.edidivi.text.toString()
            area.cant_emple = binding.edicant.text.toString().toInt()
            val res =  area.actualizar()
            if (res){
                Toast.makeText(this.requireContext(),"INFORMACIÓN MODIFICADA CORRECTAMENTE",Toast.LENGTH_LONG).show()
                mostrarD()
                binding.edidescri.setText("")
                binding.edidivi.setText("")
                binding.edicant.setText("")
            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("HAY UN ERROR")
                    .setMessage("NO SE ACTUALIZO")
                    .show()
            }
        }
        return root
    }


    //FUNCIÓN PARA MOSTRAR LOS DATOS
    private fun mostrarD(){
        try{
            var listA = Area(this.requireContext()).mostrar()
            var descAr = ArrayList<String>()
            listIdA.clear()
            (0..listA.size-1).forEach{
                var ar = listA.get(it)
                var dat = "Descripcion: "+ar.descr+"\nDivision: "+ar.divi+"\nCantidad Empleados"+ar.cant_emple+"\n"
                descAr.add(dat)
                listIdA.add(ar.area_id)
            }
            binding.listArea.adapter = ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_list_item_1,descAr)
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