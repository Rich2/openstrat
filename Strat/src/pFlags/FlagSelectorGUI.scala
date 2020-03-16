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
  val btnLeft = clickButton("<", (mb: MouseButton) => { scrollLeft }).slate(-150, headerYpos)
  val btnRight = clickButton(">", (mb: MouseButton) => { scrollRight }).slate(-100, headerYpos)   
  val btnUp = clickButton("up", (mb: MouseButton) => { scrollUp }).slate(-300, headerYpos)
  val btnDown = clickButton("down", (mb: MouseButton) => { scrollDown }).slate(-225, headerYpos)   
  val everythingNotFlag = Refs(background, aTitle, btnLeft, btnRight, btnUp, btnDown)

  var viewIndex: Int = 0
  var selectedFlagIndex: Int = -1
  def scrollRight(): Unit = { showGridView( 'Horizontal, Math.min( viewIndex+flagsPerCol ,flagCount%pageSize+1 ) ) }
  def scrollLeft(): Unit = { showGridView( 'Horizontal, Math.max( viewIndex-flagsPerCol ,0 ) ) }
  def scrollDown(): Unit = { showGridView( 'Vertical, Math.min( viewIndex+flagsPerRow ,flagCount%pageSize+1 ) ) }
  def scrollUp(): Unit = { showGridView( 'Vertical, Math.max( viewIndex-flagsPerRow ,0 ) ) }
//**NB Scroll <> print col by row
//     Scroll ^\/ print row then col
//NB2  From Left | Right 
  def showGridView(scrollDirection: Symbol, firstFlagIndexToShow:Int): Unit =
  { var r1 = listOfFlags(firstFlagIndexToShow).parent((firstFlagIndexToShow).toString).scale(commonScale)

    var pageOfFlags:Refs[PolyParent] = Refs( r1.slate( firstFlagsPosition ))
    if (scrollDirection == 'Vertical) {
      for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if i+j > 0; if firstFlagIndexToShow+i+j*flagsPerRow < flagCount)
      { val thisIndex = firstFlagIndexToShow+i+j*flagsPerRow
        r1 = listOfFlags(thisIndex).parent((thisIndex).toString).scale(commonScale)
        pageOfFlags = pageOfFlags +- r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
      }
    } else if (scrollDirection == 'Horizontal) {
      for( i <- 0 to flagsPerRow-1 ; j <- 0 to flagsPerCol-1 if i+j > 0; if firstFlagIndexToShow+j+i*flagsPerCol < flagCount)
      { val thisIndex = firstFlagIndexToShow+j+i*flagsPerCol
        r1 = listOfFlags(thisIndex).parent((thisIndex).toString).scale(commonScale)
        pageOfFlags = pageOfFlags +- r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
      }
    }

    repaint(everythingNotFlag ++ pageOfFlags)
    viewIndex = firstFlagIndexToShow
  }

  showGridView('Horizontal, viewIndex)

  mouseUp = (v, button: MouseButton, clickList) => button match
    {
      case LeftButton => clickList match
      { case List(MButtonCmd(cmd)) => cmd.apply(button)
        case List(flagIndex: Int) =>
        { deb(flagIndex.toString)
          selectedFlagIndex = flagIndex//.toString.toInt
        }
        case l => deb(l.toString)
      }
    case _ => deb("uncaught non left mouse button")
  }
}
