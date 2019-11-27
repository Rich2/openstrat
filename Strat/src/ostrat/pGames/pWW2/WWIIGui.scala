/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pWW2
import geom._, pEarth._, pCanv._, pStrat._

case class WWIIGui(canv: CanvasPlatform, scen: WWIIScen) extends EarthAllGui("World War II")
{
  statusText --= "Left click on unit to select, right click to move."
  focusUp = true
  override def saveNamePrefix = "WW2"

  val fHex: OfETile[W2Tile, W2Side] => GraphicElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = etog.vertDispVecs.fillActive(colour, tile)
      //val sides = etog.ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
      val textOrUnit: GraphicElems = ifScaleCObjs(68, tile.lunits match
        { case s if tScale > 68 & s.nonEmpty => Arr(UnitCounters.infantry(30, s.head, s.head.colour,tile.colour).slate(cen))
          case _ =>
          { val strs: Arr[String] = Arr(xyStr, cenLL.degStr)
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)
          }
        }
      )
      poly ++ textOrUnit
    }
    
  def fSide: OfESide[W2Tile, W2Side] => GraphicElems = ofs =>
    {
      import ofs._
      ifScaleCObjs(60, side.terr match
        { case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
          case Straitsold => Arr(vertDispLine.draw(6, Colour.Blue))
        }
      )
   } 
   
  //def dSides: GraphicElems = ofSidesDisplayFold(fSide)//(OfHexSideReg.implicitBuilder(_, _, _))
      
  def ls: GraphicElems = 
  { val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this) )
    as ++ gs
  }   
  
  mapPanel.mouseUp = (v, button: MouseButton, clickList) => button match
    {
      case LeftButton =>
      { selected = clickList.fHead(Arr(), Arr(_))
        statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
        eTop()
      }

      case RightButton => (selected, clickList) match
      { case (Arr(army: Army), Arr(newTile: W2Tile)) =>
        { army.tile.lunits = army.tile.lunits.removeFirst(_ == army)
          val newArmy = army.copy(newTile)
          newTile.lunits :+= newArmy
          selected = Arr(newArmy)
          repaintMap
        }
        case (Arr(army: Army), as) => debvar(as.length)
        case _ =>
      }
      case _ =>
    }
  scale = 1.08.km
  eTop()
  loadView
  repaintMap
}
