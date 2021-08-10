/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._, reflect.ClassTag

/** To be removed. Although including the cood could be considered to break the principle of encapsulation, A tile should not need to know where it is
 *  in a grid. I think it is necessary. Although the cood is determined by its position in the array there is just no good way for this data to be
 *  recovered by the Grid for random access. I think also it might be better to change to a var. */
trait OfGridElem[TileT <: TileAncient, SideT <: TileSideAncient, GridT <: TileGridAncient[TileT, SideT]]
{ def grid: GridT
  def cood: Cood   
  def coodToDispVec2(inp: Cood): Pt2
  def xyStr = cood.xyStr
  def yxStr = cood.yxStr
  /** The number of pixels grid unit. The number of pixels per X Cood. Called pSc so it doesn't class with pScale from another class when the OfTile
  *   object's members are imported */
  def psc: Double
  /** The number of pixels per tile, centre to centre */
  def tScale: Double = psc * grid.xStep

  /** If the scale is equal or greater than the parameter value return the GraphicElems of the second parameter else return an empty Arr. */
  def ifScaleCObjs(ifScale: Double, cObjs: => GraphicElems): GraphicElems = if (tScale >= ifScale) cObjs else Arr()

  def ifScaleCObj(ifScale: Double, cObj: GraphicElem *): GraphicElems = if (tScale > ifScale) cObj.toArr else Arr()

  def ifScaleIfCObj(ifScale: Double, b: Boolean, cObjs: GraphicElem *): GraphicElems = if (tScale > ifScale && b) cObjs.toArr else Arr()

  def ifScaleOptObjs[A >: Null <: AnyRef](ifScale: Double, eA: OptRef[A])(f: A => GraphicElems): GraphicElems =
    if (tScale < ifScale) Arr() else eA.fld(Arr(), f(_))
}

/** I am happy with the fundamental concept behind the OfTile traits, documentation later */
trait OfTile[TileT <: TileAncient, SideT <: TileSideAncient, GridT <: TileGridAncient[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{ def tile: TileT    
  final def cood: Cood = tile.cood   
  def vertCoods: Coods = grid.vertCoodsOfTile(cood)
  def vertDispVecs: Polygon
  def cen: Pt2
  def ownSideLines: LineSegs
}

trait OfSide[TileT <: TileAncient, SideT <: TileSideAncient, GridT <: TileGridAncient[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{ def side: SideT    
  final def cood: Cood = side.cood   
  def coodsLine: CoodLine = grid.vertCoodLineOfSide(cood)
  def vertDispLine: LineSeg = coodsLine.toLine2(coodToDispVec2)

  def ifTiles[A <: AnyRef](f: (TileT, TileT) => Boolean, fA: (TileT, TileT) => A)(implicit ct: ClassTag[A]): Arr[A] =
    grid.optSidesTiles(cood) match
  { case (Some(t1), Some(t2)) => if (f(t1, t2)) Arr(fA(t1, t2)) else Arr()
    case _ => Arr()
  }
}