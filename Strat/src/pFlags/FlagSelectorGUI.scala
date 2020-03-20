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
  val itemCount: Int = 444//listOfFlags.length
  val itemsPerRow: Int = 4
  val itemsPerCol: Int = 5
  val itemsPerPage: Int = itemsPerRow * itemsPerCol
  val pages: Int = 1 + ( itemCount - 1 ) / itemsPerPage

  var listOfFlags = Refs[Flag]()
  for( i <- 0 to itemCount-1 ) { 
    val thisColor = Colour.fromInts( scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55 )
    listOfFlags = listOfFlags ++ Refs( TextFlagMaker( i.toString, thisColor ) )
  }

  var selectedIndex = -1
  val dimensions = Map( "width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110 )
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2
  val firstFlagsPosition = ( -( dimensions("width")-dimensions("cellWidth") ) / 2 vv ( dimensions("height") - dimensions("cellHeight") ) / 2 - dimensions("headerSize") )

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic( "Scroll: less/more buttons, arrow/pgUp/Dn keys, mouse wheel", 12, 100 vv headerYpos + 10 )
  val aTitleB = TextGraphic( "TODO: drag bar, spring scroll, home/end keys", 12, 100 vv headerYpos - 6 )
  val aTitleC = TextGraphic( "click base, touch aware, pixel perfection, transitions, clipped scroll", 12, 100 vv headerYpos-22 )
  val btnMore = clickButton( "More", ( mb: MouseButton) => { scrollMore } ).slate( -100, headerYpos )
  val btnLess = clickButton( "Less", ( mb: MouseButton) => { scrollLess } ).slate( -300, headerYpos )   
  val barBackground =  Rectangle.curvedCorners(102, 32, 10, (-200 vv headerYpos)).fill(Black)  
  val scrollBar =  Rectangle.curvedCorners( Math.min( Math.max( 100.0 * itemsPerPage / itemCount, 100 ), 20 ), 30, 10 ).fill( Pink ).slate( -240, headerYpos )   
  val everythingNotFlag: Refs[GraphicElem] = Refs( background, aTitle, aTitleB, aTitleC, btnMore, btnLess, barBackground )

  var viewIndex, itemsPerUnitScroll, iScrollStep, jScrollStep: Int = 0
  val isScrollHorizontal: Boolean = true
  // if ( isScrollHorizontal ) { itemsPerUnitScroll = itemsPerCol; iScrollStep = itemsPerUnitScroll; jScrollStep = 1 }
  // else                      { itemsPerUnitScroll = itemsPerRow; iScrollStep = 1; jScrollStep = itemsPerUnitScroll }
  if ( isScrollHorizontal ) { itemsPerUnitScroll = itemsPerPage; iScrollStep = itemsPerPage; jScrollStep = 1 }
  else                      { itemsPerUnitScroll = itemsPerPage; iScrollStep = 1; jScrollStep = itemsPerPage }
deb("itemsPerUnitScroll: "+itemsPerUnitScroll.toString)  
  val barWidth = ( 20 + Math.min( 80, 80.0 * itemsPerPage / itemCount ) )
  val barAvailable = 100 - barWidth
  val barStart = -barAvailable/2
  val maxIndexOfFirstItemInView = itemsPerUnitScroll * ( ( Math.max(0, itemCount - itemsPerPage + itemsPerUnitScroll - 1)) / itemsPerUnitScroll )
  def scrollMore(): Unit = { showGridView( Math.min( viewIndex + itemsPerUnitScroll, maxIndexOfFirstItemInView ) ) }
  def scrollLess(): Unit = { showGridView( Math.max( viewIndex - itemsPerUnitScroll, 0 ) ) }

  def showGridView( indexOfFirstItemInView:Int): Unit =
  { var viewableItems:Refs[PolyParent] = Refs()
    for( j <- 0 to itemsPerCol-1; i <- 0 to itemsPerRow-1 if indexOfFirstItemInView + i*iScrollStep + j*jScrollStep < itemCount)
    { val thisIndex = indexOfFirstItemInView + i*iScrollStep + j*jScrollStep
      var thisFlag = listOfFlags( thisIndex ).parent( thisIndex.toString ).scale( commonScale )
      if ( thisIndex == selectedIndex ) thisFlag = thisFlag.scale( 0.75 )
      viewableItems = viewableItems +- thisFlag.slate( i * dimensions("cellWidth"), -j * dimensions("cellHeight") ).slate( firstFlagsPosition )
   }
    
    val barOffsetX = if ( maxIndexOfFirstItemInView != 0 ) barAvailable * indexOfFirstItemInView * 1.0 / maxIndexOfFirstItemInView else 0
    repaint( everythingNotFlag ++ viewableItems ++ Refs( Rectangle.curvedCorners( barWidth, 30, 10 )
      .fill( Pink ).slate( -200 + barStart, headerYpos ).slate( barOffsetX, 0 ) ) )

    viewIndex = indexOfFirstItemInView
  }

  showGridView( viewIndex )

  mouseUp = ( v, button: MouseButton, clickList ) => button match
  { case LeftButton => clickList match
    { case List( MButtonCmd(cmd) ) => cmd.apply( button )
      case List( flagIndex ) =>
        { selectedIndex = flagIndex.toString.toInt
          showGridView( viewIndex ) 
        } 
      case l => deb(l.toString)
    }
    case _ => deb("uncaught non left mouse button")
  }

  canv.mouseDown = ( v, b ) => ()

  canv.keyDown = ( thekey ) => thekey match
  { case ("ArrowUp" | "ArrowLeft") => scrollLess
    case ("ArrowDown" | "ArrowRight" ) => scrollMore
    case ("PageDown") => showGridView( Math.min( viewIndex + itemsPerPage, maxIndexOfFirstItemInView ) )
    case ("PageUp") => showGridView( Math.max( viewIndex - itemsPerPage, 0 ) )
    case ("End") => showGridView( maxIndexOfFirstItemInView )
    case ("Home") => showGridView( 0 )
    case _ => deb(thekey)
  }
  canv.onScroll = ( isScrollUp ) => if ( isScrollUp )  scrollLess else scrollMore
}
