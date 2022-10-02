/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import pgui._, prid._, psq._, geom._

/** This uses the new Gui. */
case class DungeonGui(canv: CanvasPlatform, scen: DungeonScen) extends CmdBarGui("Dungeon Gui")
{
  statusText = "Welcome to Dungeon Gui"
  implicit def gSys: SqGridSys = scen.gSys
  val scale: Double = gSys.fullDisplayScale(mainWidth, mainHeight)
  var focus: Vec2 = gSys.cenVec
  implicit val proj: SqSysProjection = gSys.projection(mainPanel)

  val terrs: SqCenLayer[DungTerr] = scen.terrs
  val tiles: GraphicElems = gSys.map{ sc => sc.polygonReg.fillTextActive(terrs(sc).colour, sc, sc.rcStr, 16, terrs(sc).colour.contrast) }
  def sls: LinesDraw = gSys.sidesDraw(Colour.White, 2.0)
  def players: Arr[PolygonCompound] = scen.characs.scSomesMap{ (sqc, cs) =>
    val poly1: Polygon = Rect(1.5, 1).insVerts(1, -0.25 pp 0.5, 0 pp 0.8, 0.25 pp 0.5).rotate(cs.facing.angle - Angle.up)
    val poly2: Polygon = poly1.scale( 0.75).slate(sqc.toPt2Reg)
    poly2.fillDrawTextActive(cs.charac.colour, cs, cs.charac.iden.toString, 16, 2.0, cs.charac.colour.contrast)
  }

  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame: GraphicElems = (tiles +% sls ++ players).slate(-focus).scale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}