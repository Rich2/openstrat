/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import geom._, prid._, phex._, pgui._

case class Bc305Gui(canv: CanvasPlatform, scenIn: BcScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("BC305 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs = scen.terrs
  val sTerrs = scen.sTerrs
  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def polyFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }
  def sides1: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Colour.DarkBlue) }
  def lines: RArr[LineSegDraw] = terrs.projLinksLineOptMap{ (ls, t1, t2 ) => ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None) }
  def lines2: RArr[LineSegDraw] = sTerrs.projFalseLinksScLineSegOptMap(proj){ (hs, ls) =>
    val t1 = terrs(hs.tile1Reg)
    val t2 = terrs(hs.tile2Reg)
    ife( t1 == t2, Some(ls.draw(t1.contrastBW)), None)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to BC305"

  def thisTop(): Unit = reTop(bTurn %: proj.buttons)

  thisTop()
  override def frame: GraphicElems = polyFills ++ sides1 ++ lines2

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}