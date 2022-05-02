package mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log

class SubDepartamento(v: Context) {
    var idSubD = -1
    var idEdi = ""
    var piso = ""
    var area_id = -1
    var area_descr = ""
    var area_div = ""
    var v = v
    var error = ""

    // FUNCION PARA INSERTAR
    fun inserta():Boolean{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        error = ""
        try{
            val tabla = baseD.writableDatabase
            var dts = ContentValues()
            //INSERCIÓN SOLO PARA ESOS TRES PORQUE ID_AREA ES AUTOINCREMENTAL
            dts.put("ID_EDIFICIO",idEdi)
            dts.put("PISO",piso)
            dts.put("ID_AREA",area_id)

            var res = tabla.insert("SUBDEPARTAMENTO",null,dts)
            if(res == -1L){
                return false
            }
        }catch (error: SQLiteException){
            this.error = error.message!!
            return false
        }finally {
            baseD.close()
        }
        return true
    }

    //FUNCIÓN PARA MOSTRAR T odo
    fun mostrar():ArrayList<SubDepartamento>{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        var subDepart = ArrayList<SubDepartamento>()
        error=""
        try{
            var tabla = baseD.readableDatabase
            var Select_dep = "SELECT S.ID_SUBDEPARTAMENTO, S.ID_EDIFICIO, S.PISO, A.DESCRIPCION, A.DIVISION, A.ID_AREA FROM SUBDEPARTAMENTO S INNER JOIN AREA A ON A-ID_AREA = S.ID_AREA"
            var cursor =  tabla.rawQuery(Select_dep, null)
            if (cursor.moveToFirst()){
                do{
                    var subD = SubDepartamento(v)
                    subD.idSubD = cursor.getInt(0)
                    subD.idEdi = cursor.getString(1)
                    subD.piso = cursor.getString(2)
                    subD.area_id = cursor.getInt(3)
                    subDepart.add(subD)
                }while (cursor.moveToNext())
            }
        }catch (error:SQLiteException){
            this.error = error.message!!
        }finally {
            baseD.close()
        }
        return subDepart
    }

    // FUNCIÓN PARA ACTUALIZAR
    fun actualizar():Boolean{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        var sub = ArrayList<SubDepartamento>()
        error=""
        try{
            val tabla = baseD.writableDatabase
            var dtsActualizarSub = ContentValues()
            dtsActualizarSub.put("ID_EDIFICIO",idEdi)
            dtsActualizarSub.put("PISO",piso)
            var actualizadosSub = tabla.update("SUBDEPARTMANETO",dtsActualizarSub,"ID_SUBDEPARTAMENTO='${idSubD}'",null)
            Log.e("ACTUALIZADOS",actualizadosSub.toString())
            if (actualizadosSub == 0){
                return false
            }
        }catch (error:SQLiteException){
            this.error =  error.message!!
        }finally {
            baseD.close()
        }
        return true
    }
}