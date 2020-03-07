/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags

import geom._, pCanv._, Colour._, pFlags._, FlagsOld._

case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale = 95
  val stuff = Refs(
    //page background & title
    Rectangle.curvedCorners(800, 600, 10).fill(Gray),
    TextGraphic("Flags", 40, 0 vv 275),
    //row 1
    Armenia.subj.scale(commonScale).slate(-300, 200),
    Austria.subj.scale(commonScale).slate(-100, 200),
    Belgium.subj.scale(commonScale).slate(100, 200),
    Chad.subj.scale(commonScale).slate(300, 200),
    //row 2
    China.subj.scale(commonScale).slate(-300, 90),
    England.subj.scale(commonScale).slate(-100, 90),
    France.subj.scale(commonScale).slate(100, 90),
    Germany.subj.scale(commonScale).slate(300, 90),
    //row 3
    Germany1871.subj.scale(commonScale).slate(-300, -20),
    Italy.subj.scale(commonScale).slate(-100, -20),
    Ireland.subj.scale(commonScale).slate(100, -20),
    Japan.subj.scale(commonScale).slate(300, -20),
    //row 4
    Russia.subj.scale(commonScale).slate(-300, -130),
    Soviet.subj.scale(commonScale).slate(-100, -130),
    Swastika.subj.scale(commonScale).slate(100, -130),
    UK.subj.scale(commonScale).slate(300, -130),
    //row 5
    US.subj.scale(commonScale).slate(-300, -240),
    WhiteFlag.subj.scale(commonScale).slate(-100, -240),
    // WhiteFlag.subj.scale(commonScale).slate(100, -240),
    // WhiteFlag.subj.scale(commonScale).slate(300, -240),
  )

  mouseUp = (v, b, s) =>
  { val str: String = s.headToStringElse("")
    val flagName = TextGraphic(str, 28, 200 vv 275)
    repaint(stuff -+  flagName)
  }

  repaint(stuff)
}
