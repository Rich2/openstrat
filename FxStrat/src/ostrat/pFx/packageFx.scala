/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package object pFx
{
   def saveRsonFile(path: String, fileName: String, output: String): Unit =
   {
      import java.io._
      val dir = new File(path)
      if (!dir.exists) dir.mkdirs
      val pw = new PrintWriter(new File(path / fileName))
      pw.write(output)
      pw.close
   }
   
   def loadRsonFile(pathFileName: String): EMon[String] = eTry(io.Source.fromFile(pathFileName).mkString)
   
   
   implicit class ImpScalaFxColour(colour: Colour)
   {      
      import scalafx.scene._
      def fx: paint.Color = paint.Color.rgb(colour.red, colour.green, colour.blue, colour.alpha/ 255.0)
   }  
}