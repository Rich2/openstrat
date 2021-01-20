package ostrat
import geom._, scalanative.unsafe._

@extern object Xlib {
  //def XOpenDisplay(display_name: CString): Ptr[Byte] = extern
}

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
    // Xlib.XOpenDisplay(null)
   }
}
