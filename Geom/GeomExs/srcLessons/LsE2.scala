/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, pgui.*

object LsE2 extends LessonGraphics
{ override def title: String = "Unknown purpose"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsE2Canv(_)

  case class LsE2Canv(canv: CanvasPlatform) extends CmdBarGui
  { override def title: String = "Lesson E1"

    statusText = "Left click to set action to Move. Middle or right click to set action to CycleColour."
    //val s = io.StdIn.readLine("Enter text.\n")
    deb("Hs")
  }
}