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
  val btnPrev = clickButton("<", (mb: MouseButton) => { prevPage }).slate(-150, headerYpos)
  val btnNext = clickButton(">", (mb: MouseButton) => { nextPage }).slate(-100, headerYpos)   
  val everythingNotFlag: Refs[GraphicElem] = Refs(background, aTitle, btnPrev, btnNext)

  var viewIndex: Int = 0
  var selectedFlagIndex: Int = -1
  def nextPage(): Unit = { showPage( Math.min( viewIndex+1 ,flagCount%pageSize+1 ) ) }
  def prevPage(): Unit = { showPage( Math.max( viewIndex-1 ,0 ) ) }
//**NB Scroll <> print col by row
//     Scroll ^\/ print row then col
//NB2  From Left | Right 
  def showPage(firstFlagIndexToShow:Int): Unit =
  { //val firstFlagToShow = thisPage * pageSize

    
    var r1 = listOfFlags(firstFlagIndexToShow).parent((firstFlagIndexToShow).toString).scale(commonScale)

    var pageOfFlags:Refs[PolyParent] = Refs( r1.slate( firstFlagsPosition ))

    for( j <- 0 to flagsPerCol-1; i <- 0 to flagsPerRow-1 if i+j > 0; if firstFlagIndexToShow+i+j*flagsPerRow < flagCount)

    { val thisIndex = firstFlagIndexToShow+i+j*flagsPerRow

      r1 = listOfFlags(thisIndex).parent((thisIndex).toString).scale(commonScale)

      pageOfFlags = pageOfFlags +- r1.slate(i*dimensions("cellWidth"), -j*dimensions("cellHeight")).slate( firstFlagsPosition )
    }

    repaint(everythingNotFlag ++ pageOfFlags)
    viewIndex = firstFlagIndexToShow
  }

  showPage(viewIndex)

  mouseUp = (v, button: MouseButton, clickList) => button match
    {
      case LeftButton => clickList match
      { case Refs1(MButtonCmd(cmd)) => cmd.apply(button)
        case Refs1(flagIndex) => 
        { deb(flagIndex.toString)
          selectedFlagIndex = flagIndex.toString.toInt
        }
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
