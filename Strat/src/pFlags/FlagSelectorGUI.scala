/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/** NB: Assumes Flag.ratio is always <=2. */
case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale = 95
  var listOfFlags: Refs[Flag] = Refs(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy,
                          Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag,
                          WhiteFlag, Armenia, WhiteFlag, WhiteFlag, Chad, WhiteFlag, WhiteFlag, WhiteFlag,
                          Chad, England, WhiteFlag, WhiteFlag, WhiteFlag, USSR, WhiteFlag, WhiteFlag,
                          WhiteFlag, UnitedKingdom, WhiteFlag)

  val flagCount = listOfFlags.length
  val flagsPerRow = 4
  val flagsPerCol = 5
  val pageSize = flagsPerRow * flagsPerCol
  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val firstFlagsPosition = (-(dimensions("width")-dimensions("cellWidth"))/2 vv (dimensions("height")-dimensions("cellHeight"))/2-dimensions("headerSize"))

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv headerYpos)
  val btnMore = clickButton("More", (mb: MouseButton) => { scrollMore }).slate(-200, headerYpos)
  val btnLess = clickButton("Less", (mb: MouseButton) => { scrollLess }).slate(-100, headerYpos)   
  val everythingNotFlag: Refs[GraphicElem] = Refs(background, aTitle, btnMore, btnLess)

  var viewIndex: Int = 0

  var flagsPerScroll = 0
  var iScrollStep = 0
  var jScrollStep = 0
  val scrollDirection = "Horizontal" //"Horizontal" | "Verticle"
  if (scrollDirection == "Horizontal")
  { flagsPerScroll = flagsPerCol
    iScrollStep = flagsPerCol
    jScrollStep = 1
  } else
  { flagsPerScroll = flagsPerRow
    iScrollStep = 1
    jScrollStep = flagsPerRow
  }
  def scrollMore(): Unit = { showGridView( scrollDirection, Math.min( viewIndex+flagsPerScroll ,flagCount%pageSize+1 ) ) }
  def scrollLess(): Unit = { showGridView( scrollDirection, Math.max( viewIndex-flagsPerScroll ,0 ) ) }

//**NB From Left | Right 
//*NB2 compact this

  def showGridView(scrollDirection: String, firstFlagIndexToShow:Int): Unit =
  { var r1 = listOfFlags(firstFlagIndexToShow).parent(firstFlagIndexToShow.toString).scale(commonScale)
    var pageOfFlags:Refs[PolyParent] = Refs( r1.slate( firstFlagsPosition ))
    for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if i+j > 0; if firstFlagIndexToShow+i*iScrollStep+j*jScrollStep < flagCount)
    { val thisIndex = firstFlagIndexToShow+i*iScrollStep+j*jScrollStep
      r1 = listOfFlags(thisIndex).parent(firstFlagIndexToShow.toString).scale(commonScale)
      pageOfFlags = pageOfFlags +- r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
    }

    repaint(everythingNotFlag ++ pageOfFlags)
    viewIndex = firstFlagIndexToShow
  }

  showGridView("Horizontal", viewIndex)

  mouseUp = (v, button: MouseButton, clickList) => button match
    {

      case LeftButton => clickList match
      { //case List(ShapeAll(1)(MButtonCmd(cmd))) => cmd.apply(button))
        case List(flagIndex:String) => deb(flagIndex)
        case l => deb(l.toString)
      }
    case _ => deb("uncaught non left mouse button")
  }
}

