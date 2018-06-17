/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pGrid

abstract class SquareGridSimple [TileT <: Tile](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: IsType[TileT]) extends SquareGrid[TileT](xTileMin, xTileMax, yTileMin, yTileMax){
   override def xStep: Int = 2
}