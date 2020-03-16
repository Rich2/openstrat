/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/** NB: Assumes Flag.ratio is always <=2. :NB: From Left | Right */
case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale: Int = 95
  var listOfFlags: Refs[Flag] = Refs(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy,
                          Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag,
                          WhiteFlag, Armenia, WhiteFlag, WhiteFlag, Chad, WhiteFlag, WhiteFlag, WhiteFlag,
                          Chad, England, WhiteFlag, WhiteFlag, WhiteFlag, USSR, WhiteFlag, WhiteFlag,
                          WhiteFlag, UnitedKingdom, WhiteFlag)

  val flagCount: Int = listOfFlags.length
  val flagsPerRow: Int = 4
  val flagsPerCol: Int = 5
  val pageSize: Int = flagsPerRow * flagsPerCol
  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val firstFlagsPosition = (-(dimensions("width")-dimensions("cellWidth"))/2 vv (dimensions("height")-dimensions("cellHeight"))/2-dimensions("headerSize"))

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv headerYpos)
  val btnMore = clickButton("More", (mb: MouseButton) => { scrollMore }).slate(-200, headerYpos)
  val btnLess = clickButton("Less", (mb: MouseButton) => { scrollLess }).slate(-100, headerYpos)   
  val everythingNotFlag: Refs[GraphicElem] = Refs(background, aTitle, btnMore, btnLess)

  var viewIndex, flagsPerScroll, iScrollStep, jScrollStep: Int = 0
  val scrollDirection:String = "Horizontal" //"Horizontal" | "Verticle"
  if (scrollDirection == "Horizontal") { flagsPerScroll = flagsPerCol; iScrollStep = flagsPerCol; jScrollStep = 1 }
  else                                 { flagsPerScroll = flagsPerRow; iScrollStep = 1; jScrollStep = flagsPerRow }

  def scrollMore(): Unit = { showGridView( scrollDirection, Math.min( viewIndex + flagsPerScroll, flagCount % pageSize + 1 ) ) }
  def scrollLess(): Unit = { showGridView( scrollDirection, Math.max( viewIndex - flagsPerScroll, 0 ) ) }

  var pageOfFlags:Refs[PolyParent] = Refs()

  def showGridView(scrollDirection: String, firstFlagIndexToShow:Int): Unit =
  { for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if firstFlagIndexToShow + i*iScrollStep + j*jScrollStep < flagCount)
    { val thisIndex = firstFlagIndexToShow + i*iScrollStep + j*jScrollStep
      val r1 = listOfFlags(thisIndex).parent(firstFlagIndexToShow.toString).scale(commonScale)
      pageOfFlags = pageOfFlags +- r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
    }

    repaint(everythingNotFlag ++ pageOfFlags)
    viewIndex = firstFlagIndexToShow
  }

  showGridView("Horizontal", viewIndex)

  mouseUp = (v, button: MouseButton, clickList) => button match
  { case LeftButton => clickList match
    { case List(MButtonCmd(cmd)) => cmd.apply(button)
      case List(flagIndex:String) => deb(flagIndex)
      case l => deb(l.toString)
    }
    case _ => deb("uncaught non left mouse button")
  }
}
