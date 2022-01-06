/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pX11
import geom._, x11._, XLib._, scalanative._, unsafe._, unsigned._

@extern object myapi {
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
     val disp = XOpenDisplay(null)
     if(disp == null) println("Cannot open display")
     else {
       println("Display opened")
       val defaultScn = XDefaultScreen(disp)
       println(s"default screen = $defaultScn")
       val rootWin = XRootWindow(disp, defaultScn)
       val bp = XBlackPixel(disp, defaultScn)
       val wp = XWhitePixel(disp, defaultScn)
       val window = XCreateSimpleWindow(disp, rootWin, 10, 10, 200.toUInt, 100.toUInt, 1.toUInt, bp, wp)
       XSelectInput(disp, window, ExposureMask | KeyPressMask)
       XMapWindow(disp, window)
       val event = stackalloc[Event]()
       var continue = true
       while(continue) {
         XNextEvent(disp, event.toPtr)
         event._1 match {
           case 12 =>
           { println("Expose")
             val gc = XDefaultGC(disp, defaultScn)
             XFillRectangle(disp, window, gc, 20, 20, 40.toUInt, 10.toUInt)
           }
           case 2 => {
             println("Key pressed")
             continue = false
           }
           case et => println(et.toString + " Unrecognised event.")
         }
       }
       XCloseDisplay(disp)
       println("Display closed")
     }
   }
}
