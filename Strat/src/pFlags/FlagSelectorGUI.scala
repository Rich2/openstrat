/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags

import geom._, pCanv._, Colour._

case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale = 95
  val listOfFlags = Array(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy,
                                 Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag)
  val flagCount = listOfFlags.length
  val flagsPerRow = 4
  val flagsPerCol = 4
  val pageSize = flagsPerRow * flagsPerCol
  val currentPage = 0
  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv dimensions("height")/2-dimensions("headerSize")/2)

   def showPage(thisPage:Int): Unit =
   { val firstFlagToShow = thisPage * pageSize
     val pageOfFlags = ijToMap(0, flagsPerRow)(0, flagsPerCol) { (i, j) =>
         val r1 = listOfFlags(firstFlagToShow).parent.scale(commonScale)
         r1.slate(i*dimensions("cellWidth"), j*dimensions("cellHeight")).slate(-300, -240)
     }
     val stuff: Refs[GraphicElem] = Refs(background, aTitle) ++ pageOfFlags
     repaint(stuff)
   }
 showPage(currentPage)








  // val stuff = Refs(
  //   //page background & title

  //   //row 1
  //   Armenia.subj.scale(commonScale).slate(-300, 200),
  //   Austria.subj.scale(commonScale).slate(-100, 200),
  //   Belgium.subj.scale(commonScale).slate(100, 200),
  //   Chad.subj.scale(commonScale).slate(300, 200),
  //   //row 2
  //   China.subj.scale(commonScale).slate(-300, 90),
  //   England.subj.scale(commonScale).slate(-100, 90),
  //   France.subj.scale(commonScale).slate(100, 90),
  //   Germany.subj.scale(commonScale).slate(300, 90),
  //   //row 3
  //   Germany1871.subj.scale(commonScale).slate(-300, -20),
  //   Italy.subj.scale(commonScale).slate(-100, -20),
  //   Ireland.subj.scale(commonScale).slate(100, -20),
  //   Japan.subj.scale(commonScale).slate(300, -20),
  //   //row 4
  //   Russia.subj.scale(commonScale).slate(-300, -130),
  //   USSR.subj.scale(commonScale).slate(-100, -130),
  //   Swastika.subj.scale(commonScale).slate(100, -130),
  //   UnitedKingdom.subj.scale(commonScale).slate(300, -130),
  //   //row 5
  //   UnitedStates.subj.scale(commonScale).slate(-300, -240),
  //   WhiteFlag.subj.scale(commonScale).slate(-100, -240),
  //   CommonShapesInFlags.subj.scale(commonScale).slate(100, -240),
  //   // WhiteFlag.subj.scale(commonScale).slate(300, -240),
  // )

  // mouseUp = (v, b, s) =>
  // { val str: String = s.headToStringElse("")
  //   val flagName = TextGraphic(str, 28, 200 vv 275)
  //  // repaint(stuff -+  flagName)
  // }

//  
}
