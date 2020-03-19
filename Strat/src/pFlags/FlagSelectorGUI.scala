/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/** NB: Assumes Flag.ratio is always <=2. :NB: From Left | Right */
case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ val commonScale: Int = 95
/*
  var listOfFlags: Refs[Flag] = Refs(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy,
                          Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag,
                          WhiteFlag, Armenia, WhiteFlag, WhiteFlag, Chad, WhiteFlag, WhiteFlag, WhiteFlag,
                          Chad, England, WhiteFlag, WhiteFlag, WhiteFlag, USSR, WhiteFlag, WhiteFlag,
                          WhiteFlag, UnitedKingdom, WhiteFlag)
*/
  val flagCount: Int = 44//listOfFlags.length
  deb(flagCount.toString)
  val flagsPerRow: Int = 4
  val flagsPerCol: Int = 5
  val pageSize: Int = flagsPerRow * flagsPerCol
  
  var listOfFlags = Refs[Flag]()
  for( i <- 0 to flagCount-1) { 
    val r = scala.util.Random.nextInt(256)
    val g = scala.util.Random.nextInt(256)
    val b = scala.util.Random.nextInt(256)
    val thisColor = Colour.fromInts(r, g, b)
    val thisItem = Rectangle(1.5, 1).fillTextActive(Violet, "Hello", "This Text")
    listOfFlags = listOfFlags ++ Refs(TextFlagMaker(i.toString, thisColor))
  }

  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val firstFlagsPosition = (-(dimensions("width")-dimensions("cellWidth"))/2 vv (dimensions("height")-dimensions("cellHeight"))/2-dimensions("headerSize"))

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv headerYpos)
  val btnMore = clickButton("More", (mb: MouseButton) => { scrollMore }).slate(-100, headerYpos)
  val btnLess = clickButton("Less", (mb: MouseButton) => { scrollLess }).slate(-300, headerYpos)   
  val barBackground =  Rectangle.curvedCorners(102, 32, 10, (-200 vv headerYpos)).fill(Black)  
  val scrollBar =  Rectangle.curvedCorners(50, 30, 10).fill(Pink).slate(-225, headerYpos)   
  val everythingNotFlag: Refs[GraphicElem] = Refs(background, aTitle, btnMore, btnLess, barBackground)

  var viewIndex, flagsPerScroll, iScrollStep, jScrollStep: Int = 0
  val isScrollHorizontal: Boolean = false
  if (isScrollHorizontal) { flagsPerScroll = flagsPerCol; iScrollStep = flagsPerScroll; jScrollStep = 1 }
  else                    { flagsPerScroll = flagsPerRow; iScrollStep = 1; jScrollStep = flagsPerScroll }

  def scrollMore(): Unit = { showGridView( isScrollHorizontal, Math.min( viewIndex + flagsPerScroll, flagCount - pageSize) ) }
  def scrollLess(): Unit = { showGridView( isScrollHorizontal, Math.max( viewIndex - flagsPerScroll, 0 ) ) }

  def showGridView(isScrollHorizontal: Boolean, firstFlagIndexToShow:Int): Unit =
  { var pageOfFlags:Refs[PolyParent] = Refs()
    for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if firstFlagIndexToShow + i*iScrollStep + j*jScrollStep < flagCount)
    { val thisIndex = firstFlagIndexToShow + i*iScrollStep + j*jScrollStep
      val thisFlag = listOfFlags(thisIndex).parent(thisIndex.toString).scale(commonScale)
      pageOfFlags = pageOfFlags +- thisFlag.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
    }
    
    repaint(everythingNotFlag ++ pageOfFlags ++ Refs(Rectangle.curvedCorners(50, 30, 10).fill(Pink).slate(-225, headerYpos).slate(firstFlagIndexToShow*2.5, 0) ))
    viewIndex = firstFlagIndexToShow
  }

  showGridView(isScrollHorizontal, viewIndex)

  mouseUp = (v, button: MouseButton, clickList) => button match
  { case LeftButton => clickList match
    { case List(MButtonCmd(cmd)) => cmd.apply(button)
      case List(flagIndex) =>
        { deb(flagIndex.toString)
        } 
      case l => deb(l.toString)
    }
    case _ => deb("uncaught non left mouse button")
  }
  canv.mouseDown = (v, b) => ()
  canv.keyDown = (thekey) => if (thekey=="ArrowUp" || thekey=="ArrowLeft") scrollLess else if (thekey=="ArrowDown" || thekey=="ArrowRight") scrollMore
  canv.onScroll = (isScrollUp) => if (isScrollUp)  scrollLess else scrollMore
}
