/* Copyright 2020 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/*    NB: Assumes Flag.ratio is always <=2. :NB: From Left | Right */
/*  TODO: drag bar, click base, spring scroll, touch, pixel, clip, effects
          separate scrollbar, vertical scrollbar */

case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ var viewIndex, itemsPerUnitScroll, iScrollStep, jScrollStep: Int = 0
  var selectedIndex = -1
/**/
  var listOfFlags: Arr[Flag] = Arr(PapuaNewGuinea, Eritrea, India, Iraq, CCCP, CzechRepublic, Colombia, Chile, Cyprus, Armenia, Austria, Belgium,
   Chad, China, England, France, Germany, Germany1871, Italy, Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag,
   CommonShapesInFlags)

  val itemCount: Int = listOfFlags.length // 224 //  
  val itemsPerRow: Int = 5  //  columns
  val itemsPerCol: Int = 3  //  rows
  val itemsPerPage: Int = itemsPerRow * itemsPerCol
  val pages: Int = 1 + (itemCount - 1) / itemsPerPage

  // listOfFlags = Arr[Flag](); for(i <- 0 to itemCount - 1) { val thisColor = Colour.fromInts(scala.util.Random.nextInt(200) + 55, scala.util.Random.nextInt(200) + 55, scala.util.Random.nextInt(200) + 55);
  // listOfFlags = listOfFlags ++ Arr(TextFlagMaker(i.toString, thisColor)) }

  val viewport = Map("width"->750, "height"->310, "headerSize"->50, "cellWidth"->150, "cellHeight"->100, "commonScale"->100)

  val scrollport = Map("maxBarWidth"->(viewport("width")  - 80), "minBarWidth"->20,
    "isScrollHorizontal"-> 1, "scrollYpos"-> (viewport("height") / 2 + viewport("headerSize") / 2))
  
  val firstFlagsPosition = (-(viewport("width") - viewport("cellWidth")) / 2 vv (viewport("height") - viewport("cellHeight")) / 2)
  val barBackground =  Rect.curvedCorners(scrollport("maxBarWidth") + 2, 32, 10, (0 vv scrollport("scrollYpos"))).fill(Black)
  val background = Rect.curvedCorners(viewport("width"), viewport("height"), 10).fill(Gray)
  val btnMore = clickButton(">", (mb: MouseButton) => { scrollMore() }).slate(+20 + scrollport("maxBarWidth") / 2, scrollport("scrollYpos"))
  val btnLess = clickButton("<", (mb: MouseButton) => { scrollLess() }).slate(-20 - scrollport("maxBarWidth") / 2, scrollport("scrollYpos"))
  val scrollBar: Arr[GraphicSimElem] = Arr(btnMore, btnLess, barBackground)

  if (scrollport("isScrollHorizontal") == 1) { itemsPerUnitScroll = itemsPerCol; iScrollStep = itemsPerCol; jScrollStep = 1 }
  else                                     { itemsPerUnitScroll = itemsPerRow; iScrollStep = 1; jScrollStep = itemsPerRow }
// set itemsPerUnitScroll = itemsPerPage >>> scroll by page rather than by line

  val scrollStep = Math.max(iScrollStep, jScrollStep)
  val barMaxAvailable = scrollport("maxBarWidth") - scrollport("minBarWidth")
  val barWidth = (scrollport("minBarWidth") + Math.min(barMaxAvailable, 1.0 * barMaxAvailable * itemsPerPage / itemCount))
  val barAvailable = scrollport("maxBarWidth") - barWidth
  val barStartX = -barAvailable / 2
  val maxIndexOfFirstItemInView = scrollStep * ((Math.max(0, itemCount - itemsPerPage + scrollStep - 1)) / scrollStep)
  def scrollMore(): Unit = { showGridView(viewIndex + itemsPerUnitScroll) }
  def scrollLess(): Unit = { showGridView(viewIndex - itemsPerUnitScroll) }
  var viewableItems:Arr[PolygonParentOld] = Arr()

  var bar = Rect.curvedCorners(barWidth, 30, 10).fill(Pink)
  var barOffsetX = 0.0
  var dragStartBarOffsetX = 0.0
  var dragStartX = 0.0
  var isDragging = false

  def showGridView(indexOfFirstItemInView:Int = 0): Unit =
  { val firstIndex = Math.min(Math.max(indexOfFirstItemInView, 0), maxIndexOfFirstItemInView)
    viewableItems = Arr()
    for(j <- 0 to itemsPerCol - 1; i <- 0 to itemsPerRow - 1 if firstIndex + i * iScrollStep + j * jScrollStep < itemCount)
    { val thisIndex = firstIndex + i * iScrollStep + j * jScrollStep
      val thisFlag = listOfFlags(thisIndex).parent(thisIndex.toString).scale(viewport("commonScale") / Math.sqrt(listOfFlags(thisIndex).ratio))
      viewableItems = viewableItems +- thisFlag.slate(i * viewport("cellWidth"), -j * viewport("cellHeight")).slate(firstFlagsPosition)
    }
    viewIndex = firstIndex
    if (selectedIndex == -1) positionBar() else showSelected()
  }

  def positionBar(): Unit = 
  { barOffsetX = if (maxIndexOfFirstItemInView != 0) barAvailable * viewIndex * 1.0 / maxIndexOfFirstItemInView else 0
    bar = Rect.curvedCorners(barWidth, 30, 10, barStartX + barOffsetX vv scrollport("scrollYpos")).fill(Pink)
    repaint(Arr(background) ++ scrollBar ++ viewableItems ++ Arr(bar))
  }

  def showSelected(): Unit =
  { viewableItems = Arr(listOfFlags(selectedIndex).parent(selectedIndex.toString).scale(3 * viewport("commonScale") / Math.sqrt(listOfFlags(selectedIndex).ratio)))
    positionBar()
  }

  showGridView(viewIndex)

  def dragging(pixelDelta: Double): Unit =
  {
    barOffsetX = dragStartBarOffsetX + pixelDelta
    barOffsetX = Math.min(barAvailable, Math.max(0, barOffsetX))
    val currentScroll = ((barOffsetX * maxIndexOfFirstItemInView) / barAvailable).toInt
    showGridView(((currentScroll + itemsPerUnitScroll - 1) / itemsPerUnitScroll * itemsPerUnitScroll))//
  }

  mouseUp = (mouseButton: MouseButton, clickList, mousePosition) => isDragging match
  { case true => isDragging = false
    case false => mouseButton match
    { case LeftButton => clickList match
      { case List(MouseButtonCmd(cmd)) => cmd.apply(mouseButton)
        case List(flagIndex) =>
        { selectedIndex = if (selectedIndex != -1) -1 else flagIndex.toString.toInt
          showGridView(viewIndex)
        } 
        case l => { selectedIndex = -1; showGridView(viewIndex) }
      }
      case _ =>
    }
  }
   
  canv.mouseDragged = (mousePosition:Vec2, mouseButton:MouseButton) => if (mouseButton == LeftButton & isDragging == true) dragging(mousePosition.x - dragStartX)

  canv.mouseDown = (mousePosition:Vec2, mouseButton:MouseButton) => mouseButton match
  { case LeftButton if (bar.boundingRect.ptInside(mousePosition) == true) =>
    { dragStartBarOffsetX = barOffsetX
      dragStartX = mousePosition.x
      isDragging = true
    }
    case LeftButton if (barBackground.boundingRect.ptInside(mousePosition) == true) =>
    { if (mousePosition.x > barStartX + barOffsetX) showGridView(viewIndex + itemsPerPage)
      else showGridView(viewIndex - itemsPerPage)
    }
    case _ =>
  }

//** NB below is for scroll ~> need focus to handle keys also for selected etc **//
  canv.keyDown = (thekey: String) => thekey match
  { case ("ArrowUp" | "ArrowLeft") => showGridView(viewIndex - itemsPerUnitScroll)
    case ("ArrowDown" | "ArrowRight") => showGridView(viewIndex + itemsPerUnitScroll)
    case ("PageDown") => showGridView(viewIndex + itemsPerPage)
    case ("PageUp") => showGridView(viewIndex - itemsPerPage)
    case ("End") => showGridView(maxIndexOfFirstItemInView)
    case ("Home") => showGridView(0)
    case _ => deb(thekey)
  }

  canv.onScroll = (isScrollLess: Boolean) => if (isScrollLess) scrollLess() else scrollMore()
}