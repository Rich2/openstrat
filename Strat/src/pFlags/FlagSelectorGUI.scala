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
  val flagCount: Int = 444//listOfFlags.length
  val flagsPerRow: Int = 4
  val flagsPerCol: Int = 5
  val pageSize: Int = flagsPerRow * flagsPerCol
  val pages: Int = 1 + ( flagCount - 1 ) / pageSize

  var listOfFlags = Refs[Flag]()
  for( i <- 0 to flagCount-1 ) { 
    val thisColor = Colour.fromInts( scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55 )
    listOfFlags = listOfFlags ++ Refs( TextFlagMaker( i.toString, thisColor ) )
  }

  var selectedIndex = -1
  val dimensions = Map( "width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110 )
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val firstFlagsPosition = ( -( dimensions("width")-dimensions("cellWidth") ) / 2 vv ( dimensions("height") - dimensions("cellHeight") ) / 2 - dimensions("headerSize") )

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic( "Scroll: less/more buttons, arrow keys, mouse wheel", 12, 100 vv headerYpos + 10 )
  val aTitleB = TextGraphic( "TODO: drag bar, spring scroll, pgUp/Dn home/end keys", 12, 100 vv headerYpos - 6 )
  val aTitleC = TextGraphic( "click base, touch aware, pixel perfection, transitions, clipped scroll, selection", 12, 100 vv headerYpos-22 )
  val btnMore = clickButton( "More", ( mb: MouseButton) => { scrollMore } ).slate( -100, headerYpos )
  val btnLess = clickButton( "Less", ( mb: MouseButton) => { scrollLess } ).slate( -300, headerYpos )   
  val barBackground =  Rectangle.curvedCorners(102, 32, 10, (-200 vv headerYpos)).fill(Black)  
  val scrollBar =  Rectangle.curvedCorners( Math.min( Math.max( 100.0 * pageSize / flagCount, 100 ), 20 ), 30, 10 ).fill( Pink ).slate( -240, headerYpos )   
  val everythingNotFlag: Refs[GraphicElem] = Refs( background, aTitle, aTitleB, aTitleC, btnMore, btnLess, barBackground )

  var viewIndex, flagsPerScroll, iScrollStep, jScrollStep: Int = 0
  val isScrollHorizontal: Boolean = false
  if ( isScrollHorizontal ) { flagsPerScroll = flagsPerCol; iScrollStep = flagsPerScroll; jScrollStep = 1 }
  else                      { flagsPerScroll = flagsPerRow; iScrollStep = 1; jScrollStep = flagsPerScroll }
  
  val barWidth = ( 20 + Math.min( 80, 80.0 * pageSize / flagCount ) )
  val barAvailable = 100 - barWidth
  val barStart = -barAvailable/2
  val lastFlagIndexToShow = flagsPerScroll * ( ( Math.max(0, flagCount - pageSize + flagsPerScroll - 1)) / flagsPerScroll )
  def scrollMore(): Unit = { showGridView( isScrollHorizontal, Math.min( viewIndex + flagsPerScroll, lastFlagIndexToShow ) ) }
  def scrollLess(): Unit = { showGridView( isScrollHorizontal, Math.max( viewIndex - flagsPerScroll, 0 ) ) }

  def showGridView(isScrollHorizontal: Boolean, firstFlagIndexToShow:Int): Unit =
  { var pageOfFlags:Refs[PolyParent] = Refs()
    for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if firstFlagIndexToShow + i*iScrollStep + j*jScrollStep < flagCount)
    { val thisIndex = firstFlagIndexToShow + i*iScrollStep + j*jScrollStep
      var thisFlag = listOfFlags( thisIndex ).parent( thisIndex.toString ).scale( commonScale )
      if ( thisIndex == selectedIndex ) thisFlag = thisFlag.scale( 0.75 )
      pageOfFlags = pageOfFlags +- thisFlag.slate( i * dimensions("cellWidth"), -j * dimensions("cellHeight") ).slate( firstFlagsPosition )
   }
    
    val barOffsetX = if ( lastFlagIndexToShow != 0 ) barAvailable * firstFlagIndexToShow * 1.0 / lastFlagIndexToShow else 0
    repaint( everythingNotFlag ++ pageOfFlags ++ Refs( Rectangle.curvedCorners( barWidth, 30, 10 )
      .fill( Pink ).slate( -200 + barStart, headerYpos ).slate( barOffsetX, 0 ) ) )

    viewIndex = firstFlagIndexToShow
  }

  showGridView( isScrollHorizontal, viewIndex )

  mouseUp = ( v, button: MouseButton, clickList ) => button match
  { case LeftButton => clickList match
    { case List( MButtonCmd(cmd) ) => cmd.apply( button )
      case List( flagIndex ) =>
        { selectedIndex = flagIndex.toString.toInt
          showGridView( isScrollHorizontal, viewIndex ) 
        } 
      case l => deb(l.toString)
    }
    case _ => deb("uncaught non left mouse button")
  }
  canv.mouseDown = ( v, b ) => ()
  canv.keyDown = ( thekey ) => if ( thekey=="ArrowUp" || thekey=="ArrowLeft") scrollLess else if ( thekey=="ArrowDown" || thekey=="ArrowRight" ) scrollMore
  canv.onScroll = ( isScrollUp ) => if ( isScrollUp )  scrollLess else scrollMore
}
