/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int]. There are 2
 * values for each row. Each row from lowest to highest has two values. The rTileMin coordinate for the row followed by the length of the row in the
 * number of tile centres [[HCen]]s. */
abstract class HGridIrr() extends HGrid
{

}