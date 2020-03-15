/* Copyright 2020 Stephen. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/** NB: Assumes Flag.ratio is always <=2. */
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
  val dimensions = Map("width"->800, "height"->600, "headerSize"->50, "cellWidth"->200, "cellHeight"->110)
  val headerYpos = dimensions("height")/2-dimensions("headerSize")/2

  val background = Rectangle.curvedCorners(dimensions("width"), dimensions("height"), 10).fill(Gray)
  val aTitle = TextGraphic("Flags", 40, 0 vv headerYpos)
  val btnPrev = clickButton("<", (mb: MouseButton) => { prevPage }).slate(-150, headerYpos)
  val btnNext = clickButton(">", (mb: MouseButton) => { nextPage }).slate(-100, headerYpos)   
  val anythingButFlags: Refs[GraphicElem] = Refs(background, aTitle, btnPrev, btnNext)

  var currentPage: Int = 0
  
  def nextPage(): Unit = { showPage(Math.min(currentPage+1 ,flagCount%pageSize))}
  def prevPage(): Unit = { showPage(Math.max(currentPage-1 ,0))}

  def showPage(thisPage:Int): Unit =
  { val firstFlagToShow = thisPage * pageSize
    val pageOfFlags = ijToMap(0, flagsPerCol-1)(0, flagsPerRow-1) { (i, j) =>
      val r1 = listOfFlags(firstFlagToShow+i+j*(flagsPerRow-1)).parent().scale(commonScale)
      r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate(
            -(dimensions("width")-dimensions("cellWidth"))/2, (dimensions("height")-dimensions("cellHeight"))/2-dimensions("headerSize"))
    }
    repaint(anythingButFlags ++ pageOfFlags)
    currentPage = thisPage
  }

  showPage(currentPage)

  mouseUp = (v, button: MouseButton, clickList) => button match
    {
      case LeftButton => clickList match
      { case List(MButtonCmd(cmd)) => cmd.apply(button)
        case List(flag: Flag) => {
          deb("This is a flag: " + flag.toString)
          val num = listOfFlags.indexOf(flag)
          deb(num.toString)
        }
        case List(stuff) => deb(stuff.toString)
        case _ => deb("uncaught left click")
      }
    case _ => deb("uncaught non left mouse button")
  }
}

  // mapPanel.mouseUp = (v, button: MouseButton, clickList) => button match
  //   {
  //     case LeftButton =>
  //     { selected = clickList//.fHead(Arr(), Arr(_))
  //       statusText = selected.headToStringElse("Nothing Clicked")
  //       eTop()
  //     }

  //     case RightButton => (selected, clickList) match
  //     { case (Refs1(army: Army), Refs1(newTile: W2TileOld)) =>
  //       { army.tile.lunits = army.tile.lunits.removeFirst(_ == army)
  //         val newArmy = army.copy(newTile)
  //         newTile.lunits +-= newArmy
  //         selected = Refs(newArmy)
  //         repaintMap
  //       }
  //       case (Refs1(army: Army), as) => debvar(as.length)
  //       case _ =>
  //     }
  //     case _ =>
  //   }
  // mouseUp = 
  // {
  //   case (v, b, Refs1(MButtonCmd(cmd))) => cmd.apply(b)
  //  // case (v, b, s) => deb(Refs1(s).toString)
  //   case (v, b, Refs1(s: Flag)) => {
  //     deb(s.toString)
  //     // deb(s(0).name + " : " & s(0).ratio )
  //     // s.parent.scale(200)
  //   }
  //   case _ => deb("nothing")
  // }
