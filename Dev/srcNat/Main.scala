/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pX11
import geom._, scalanative.unsafe._

@link("X11")
@extern object Xlib {
  def XOpenDisplay(display_name: CString): Ptr[CStruct2[CInt, CInt]] = extern
  def XCloseDisplay(disp: Display): Unit = extern
  def XRootWindow(disp: Display, screen_number: CInt): Window = extern
}

@extern
object myapi {
  def add3(in: CLong): CLong = extern
}

 object Main
 {
   import Xlib._
   def main(args: Array[String]): Unit =
   {
   	 val i: Long = myapi.add3(-3L)
   	 println(i)
   	 val p1 = Pt2(5.6, 7)
     deb("Hello, native world!, p1 = " + p1.toString)
     val disp = XOpenDisplay(null)
     if(disp == null) println("Cannot open display")
     else {
       println("Display opened")
       XCloseDisplay(disp)
       println("Display closed")
     }
   }
}
