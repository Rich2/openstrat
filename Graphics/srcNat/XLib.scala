/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package x11
import scalanative.unsafe._

@link("X11") @extern object XLib
{ def XOpenDisplay(display_name: CString): Ptr[CStruct2[CInt, CInt]] = extern
  def XCloseDisplay(disp: Display): Unit = extern
  def XDefaultScreen(disp: Display): CInt = extern
  def XRootWindow(disp: Display, screen_number: CInt): Window = extern
  def XBlackPixel(disp: Display, scren_number: CInt): CUnsignedLong = extern
  def XWhitePixel(disp: Display, scren_number: CInt): CUnsignedLong = extern
  def XCreateSimpleWindow(disp: Display, parent: Window, x: CInt, y: CInt, width: CUnsignedInt, height: CUnsignedInt, border_width: CUnsignedLong,
                          border: CUnsignedLong, background: CUnsignedLong): Window = extern
  def XSelectInput(disp: Display, window: Window, event_mask: CLong): Unit = extern
  def XMapWindow(disp: Display, window: Window): Unit = extern
  def XDefaultGC(disp: Display, screen_number: CInt): GC = extern
  def XFillRectangle(disp: Display, drawable: Window, gc: GC, x: CInt, y: CInt, width: CUnsignedInt, height: CUnsignedInt): Unit = extern
  def XNextEvent(disp: Display, eventPtr: EventPtr): Unit = extern
}
