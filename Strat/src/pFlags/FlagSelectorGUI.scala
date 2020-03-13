/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags

import geom._, pCanv._, Colour._


//NB: Assumes Flag.ratio is always <=2

case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale = 95
  val listOfFlags = Array(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy,
                          Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag,
                          WhiteFlag, Armenia, WhiteFlag, WhiteFlag, Chad, WhiteFlag, WhiteFlag, WhiteFlag,
                          Chad, England, WhiteFlag, WhiteFlag, WhiteFlag, USSR, WhiteFlag, WhiteFlag,
                          WhiteFlag, UnitedKingdom, WhiteFlag, CommonShapesInFlags, CommonShapesInFlags)

  val flagCount = listOfFlags.length
  val flagsPerRow = 5
  val flagsPerCol = 4
  val pageSize = flagsPerRow * flagsPerCol
  val currentPage = 0
  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)
deb("hi")
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv headerYpos)

  def clickButton(str: String, cmd: MB0, backColour: Colour = Colour.White) =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(MButtonCmd((mb: MouseButton) => { showPage(1) }), backColour, 3, backColour.contrastBW, 25, str)
  val btnPrev = clickButton("<", (mb: MouseButton) => { deb("THIS NEVER HAPPENS") }).slate(-150, headerYpos)
  val btnNext = clickButton(">", (mb: MouseButton) => { deb("THIS NEVER HAPPENS") }).slate(-100, headerYpos)   
  val btns: Refs[ShapeParent] = Refs(btnPrev, btnNext)

  var stuff: Refs[GraphicElem] = Refs(background, aTitle)
  
  def showPage(thisPage:Int): Unit =
  { val firstFlagToShow = thisPage * pageSize
    val pageOfFlags = ijToMapOld(0, flagsPerCol-1)(0, flagsPerRow-1) { (i, j) =>
      val r1 = listOfFlags(firstFlagToShow+i+j*(flagsPerRow-1)).parent.scale(commonScale)
      r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate(
            -(dimensions("width")-dimensions("cellWidth"))/2, (dimensions("height")-dimensions("cellHeight"))/2-dimensions("headerSize"))
    }
    stuff = Refs(background, aTitle) ++ pageOfFlags ++ btns
    repaint(stuff)
  }

  showPage(currentPage)

  mouseUp = (v, b, s) =>
  { 
    if(v._1 < -80 & v._1 > -120 & v._2 > 260 & v._2 < 285){ showPage(Math.min(currentPage+1 ,flagCount%pageSize)) }
    else if(v._1 < -130 & v._1 > -170 & v._2 > 260 & v._2 < 285){ showPage(Math.max(currentPage-1 ,0)) }
    else{
      val str: String = s.headToStringElse("")
      val flagName = TextGraphic(str.toString, 28, 200 vv 275)
      repaint(stuff +- flagName)
    }
  }
}
