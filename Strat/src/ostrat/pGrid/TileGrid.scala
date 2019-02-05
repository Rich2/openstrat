/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** A tileGrid is a collection of tiles, either hexs or squares. This is a fundamental trait. It is a specific case of a tiled area. I
 *  have reached the conclusion that the general case of completely irregular tiling, while interesting mathematically and useful for say
 *  representing a historical game like "Risk", has insufficient utility for the representations we want today. Tile rids can however be fully regular
 *  or partly irregular such as the grids for covering the Earth's surface. Grids can be simple just comsisting of values for the tiles or complex
 *  containing values for the tiles and the tile sides. Rivers, straits, walls, ditches are examples of values commonly assigned to tile sides.  
 *  
 *  It is stored in an underlying array. It consists of a sequence of contiguous rows of tiles. Each row of tiles is itself contiguous,
 *  There are no breaks between the first tile of the row and the last tile of the row although a row can consist of a single tile. Every
 *  row shares at least one tile side with the row above and below. The grid includes all the sides of the tiles including the sides on
 *  the outer edges of the grid. This means to link two grids requires a Grid Bridge class. */
trait TileGrid[TileT <: Tile]
{
  def xTileMin: Int
  def xTileMax: Int
  def yTileMin: Int
  def yTileMax: Int
  //val arr: Array[AnyRef]
  def evTile: IsType[TileT]
  def xArrLen: Int
  def yArrLen: Int
  lazy val arr: Array[AnyRef] = new Array[AnyRef](yArrLen * xArrLen)
  def xToInd(x: Int): Int
  def yToInd(y: Int): Int
  def xyToInd(x: Int, y: Int) = xToInd(x) + yToInd(y)
  val yRatio: Double
  def xStep: Int
  
  def getTile(x: Int, y: Int): TileT// = { coodIsTile(x, y); evTile.asType(arr(xyToInd(x, y))) }   
  def getTile(tc: Cood): TileT// = { coodIsTile(tc); evTile.asType(arr(xyToInd(tc.x, tc.y))) }
   
  def optTile(x: Int, y: Int): Option[TileT]
  final def optTile(cood: Cood): Option[TileT] = optTile(cood.x, cood.y)
}