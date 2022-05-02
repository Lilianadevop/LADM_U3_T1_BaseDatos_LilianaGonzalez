package mx.edu.ittepic.ladm_u3_t1_basedatos_lilianagonzalez

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.fragment.app.Fragment
import android.database.sqlite.SQLiteException
import android.util.Log

class Area(v: Context) {
    var area_id = -1
    var descr = ""
    var divi = ""
    var cant_emple = -1
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
            dts.put("DESRIPCION",descr)
            dts.put("DIVISION",divi)
            dts.put("CANTIDAD_EMPLEADOS",cant_emple)

            var res = tabla.insert("AREA",null,dts)
            if(res == -1L){
                return false
            }
        }catch (error:SQLiteException){
            this.error = error.message!!
            return false
        }finally {
            baseD.close()
        }
        return true
    }

    // FUNCIÓN PARA MOSTRAR
    fun mostrar():ArrayList<Area>{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        var ar = ArrayList<Area>()
        error=""
        try{
            var tabla = baseD.readableDatabase
            var Select = "SELECT * FROM AREA"
            var cursor =  tabla.rawQuery(Select, null)
            if (cursor.moveToFirst()){
                do{
                    var area = Area(v)
                    area.area_id = cursor.getInt(0)
                    area.descr = cursor.getString(1)
                    area.divi = cursor.getString(2)
                    area.cant_emple = cursor.getInt(3)
                    ar.add(area)
                }while (cursor.moveToNext())
            }
        }catch (error:SQLiteException){
            this.error = error.message!!
        }finally {
            baseD.close()
        }
        return ar
    }

    //CUNSULTA POR DESCRIPCION Y DIVISION
    fun consultapor(como:String,cual:String):ArrayList<Area>{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        var ar = ArrayList<Area>()
        error=""
        try{
            var tabla = baseD.readableDatabase
            if (cual.equals("DESCRIPCION")){
                var SelectDes = "SELECT FROM AREA WHERE DESCRIPCION LIKE '${como}%'"
                Log.e("ERROR","EN CONTULTAR ${SelectDes}")
                var cursor = tabla.rawQuery(SelectDes,null)
                if(cursor.moveToFirst()){
                    do{
                        var area = Area(v)
                        area.area_id = cursor.getInt(0)
                        area.descr = cursor.getString(1)
                        area.divi = cursor.getString(2)
                        area.cant_emple = cursor.getInt(3)
                        ar.add(area)
                    }while (cursor.moveToNext())
                }
            }
            if (cual.equals("DIVISION")){
                var SelectDiv = "SELECT FROM AREA WHERE DIVISION LIKE '${como}%'"
                Log.e("ERROR","EN CONTULTAR ${SelectDiv}")
                var cursor = tabla.rawQuery(SelectDiv,null)
                if(cursor.moveToFirst()){
                    do{
                        var area = Area(v)
                        area.area_id = cursor.getInt(0)
                        area.descr = cursor.getString(1)
                        area.divi = cursor.getString(2)
                        area.cant_emple = cursor.getInt(3)
                        ar.add(area)
                    }while (cursor.moveToNext())
                }
            }
        }catch (error:SQLiteException){
            this.error = error.message!!
        }finally {
            baseD.close()
        }
        return ar

    }

    // FUNCIÓN PARA ACTUALIZAR
    fun actualizar():Boolean{
        var baseD = BaseDatos(v, "Base_Datos",null,1)
        var ar = ArrayList<Area>()
        error=""
        try{
            val tabla = baseD.writableDatabase
            var dtsActualizar = ContentValues()
            dtsActualizar.put("DESCRIPCION",descr)
            dtsActualizar.put("DIVISION",divi)
            dtsActualizar.put("CANTIDAD_EMPLEADOS",cant_emple)
            var actualizados = tabla.update("AREA",dtsActualizar,"ID_AREA='${area_id}'",null)
            Log.e("ACTUALIZADOS",actualizados.toString())
            if (actualizados == 0){
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