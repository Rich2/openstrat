/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/*    NB: Assumes Flag.ratio is always <=2. :NB: From Left | Right */
/*  TODO: drag bar, click base, spring scroll, touch, pixel, clip, effects */

case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ var viewIndex, itemsPerUnitScroll, iScrollStep, jScrollStep: Int = 0
  var selectedIndex = -1
/**/
  var listOfFlags: Refs[Flag] = Refs(Armenia, Austria, Belgium, Chad, China, England, France, Germany, Germany1871, Italy, Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag, CommonShapesInFlags, WhiteFlag, WhiteFlag, Armenia, WhiteFlag, WhiteFlag, Chad, WhiteFlag, WhiteFlag, WhiteFlag, Chad, England, WhiteFlag, WhiteFlag, WhiteFlag, USSR, WhiteFlag, WhiteFlag, WhiteFlag, UnitedKingdom, WhiteFlag)

  val itemCount: Int = listOfFlags.length
  val itemsPerRow: Int = 5  //  columns
  val itemsPerCol: Int = 3  //  rows
  val itemsPerPage: Int = itemsPerRow * itemsPerCol
  val pages: Int = 1 + ( itemCount - 1 ) / itemsPerPage

//  var listOfFlags = Refs[Flag](); for( i <- 0 to itemCount-1 ) { val thisColor = Colour.fromInts( scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55, scala.util.Random.nextInt( 200 ) + 55 ); listOfFlags = listOfFlags ++ Refs( TextFlagMaker( i.toString, thisColor ) ) }

  val viewport = Map( "width"->750, "height"->310, "headerSize"->50, "cellWidth"->150, "cellHeight"->100, "maxBarWidth"->(750-80), "minBarWidth"->20, "isScrollHorizontal"-> 1, "commonScale"->100 )
  val headerYpos = viewport("height")/2+viewport("headerSize")/2
  val firstFlagsPosition = ( -( viewport("width")-viewport("cellWidth") ) / 2 vv ( viewport("height") - viewport("cellHeight") ) / 2 )

  val background = Rectangle.curvedCorners(viewport("width"), viewport("height"), 10).fill(Gray)
  val aTitle = TextGraphic( "Scroll: less/more buttons, arrow/pgUp/pgDn/Home/End keys, mouse wheel", 12, 0 vv headerYpos + 30 )
  val btnMore = clickButton( ">", ( mb: MouseButton) => { scrollMore } ).slate( +20+viewport("maxBarWidth")/2, headerYpos )
  val btnLess = clickButton( "<", ( mb: MouseButton) => { scrollLess } ).slate( -20-viewport("maxBarWidth")/2, headerYpos )   
  val barBackground =  Rectangle.curvedCorners( viewport("maxBarWidth") + 2, 32, 10, (0 vv headerYpos)).fill(Black)
  val everythingNotItemOrScrollbar: Refs[GraphicElem] = Refs( background, aTitle, btnMore, btnLess, barBackground )
//val barRect
  if ( viewport("isScrollHorizontal") == 1 ) { itemsPerUnitScroll = itemsPerCol; iScrollStep = itemsPerCol; jScrollStep = 1 }
  else                                       { itemsPerUnitScroll = itemsPerRow; iScrollStep = 1; jScrollStep = itemsPerRow }
// set itemsPerUnitScroll = itemsPerPage >>> scroll by page rather than by line

  val scrollStep = Math.max(iScrollStep, jScrollStep)
  val barMaxAvailable = viewport("maxBarWidth") - viewport("minBarWidth")
  val barWidth = ( viewport("minBarWidth") + Math.min( barMaxAvailable, 1.0 * barMaxAvailable * itemsPerPage / itemCount ) )
  val barAvailable = viewport("maxBarWidth") - barWidth
  val barStart = -barAvailable/2
  val scrollBar =  Rectangle.curvedCorners( Math.min( Math.max( 1.0 * barAvailable * itemsPerPage / itemCount, barWidth ), viewport("minBarWidth") ), 30, 10 ).fill( Pink ).slate( -240, headerYpos )   
  val maxIndexOfFirstItemInView = scrollStep * ( ( Math.max(0, itemCount - itemsPerPage + scrollStep - 1)) / scrollStep )
  def scrollMore(): Unit = { showGridView( Math.min( viewIndex + itemsPerUnitScroll, maxIndexOfFirstItemInView ) ) }
  def scrollLess(): Unit = { showGridView( Math.max( viewIndex - itemsPerUnitScroll, 0 ) ) }

  def showGridView( indexOfFirstItemInView:Int): Unit =
  { var viewableItems:Refs[PolyParent] = Refs()
    for( j <- 0 to itemsPerCol-1; i <- 0 to itemsPerRow-1 if indexOfFirstItemInView + i * iScrollStep + j * jScrollStep < itemCount)
    { val thisIndex = indexOfFirstItemInView + i * iScrollStep + j * jScrollStep
      var thisFlag = listOfFlags( thisIndex ).parent( thisIndex.toString ).scale( viewport("commonScale")/Math.sqrt(listOfFlags( thisIndex ).ratio ) )
      if ( thisIndex == selectedIndex ) thisFlag = thisFlag.scale( 0.75 )
      viewableItems = viewableItems +- thisFlag.slate( i * viewport("cellWidth"), -j * viewport("cellHeight") ).slate( firstFlagsPosition )
   }
    
    val barOffsetX = if ( maxIndexOfFirstItemInView != 0 ) barAvailable * indexOfFirstItemInView * 1.0 / maxIndexOfFirstItemInView else 0
    repaint( everythingNotItemOrScrollbar ++ viewableItems ++ Refs( Rectangle.curvedCorners( barWidth, 30, 10 )
      .fill( Pink ).slate( barStart, headerYpos ).slate( barOffsetX, 0 ) ) )

    viewIndex = indexOfFirstItemInView
  }

  showGridView( viewIndex )

  mouseUp = ( v, button: MouseButton, clickList ) => button match
  { case LeftButton => clickList match
    { case List( MButtonCmd(cmd) ) => cmd.apply( button )
      case List( flagIndex ) =>
        { selectedIndex = if (selectedIndex == flagIndex.toString.toInt) -1 else flagIndex.toString.toInt
          showGridView( viewIndex ) 
        } 
      case l => deb(l.toString)
    }
    case _ => deb("uncaught non left mouse button")
  }
  canv.mouseDragged = (v:Vec2, b:MouseButton) => deb("mouse dragging.........")

  // canv.mouseDown = ( v:Vec2, b:MouseButton ) => deb("mouse down on bar: "+barBackground.rect.toString)

  canv.keyDown = ( thekey: String ) => thekey match
  { case ("ArrowUp" | "ArrowLeft") => scrollLess
    case ("ArrowDown" | "ArrowRight" ) => scrollMore
    case ("PageDown") => showGridView( Math.min( viewIndex + itemsPerPage, maxIndexOfFirstItemInView ) )
    case ("PageUp") => showGridView( Math.max( viewIndex - itemsPerPage, 0 ) )
    case ("End") => showGridView( maxIndexOfFirstItemInView )
    case ("Home") => showGridView( 0 )
    case _ => deb(thekey)
  }
  canv.onScroll = ( isScrollLess: Boolean ) => if ( isScrollLess )  scrollLess else scrollMore
}
