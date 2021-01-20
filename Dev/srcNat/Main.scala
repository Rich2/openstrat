package ostrat
import geom._
import scalanative.unsafe._

@extern
object myapi {
  def add3(in: CLong): CLong = extern
}

 object Main
 {
   def main(args: Array[String]): Unit =
   {
   	val i: Long = myapi.add3(-3L)
   	 println(i)
   	 val p1 = Pt2(5.6, 7)
     deb("Hello, native world!, p1 = " + p1.toString)
   }
}
