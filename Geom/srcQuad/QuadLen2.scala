/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait QuadLen2[+VT <: PtLen2] extends PolygonLen2P4[VT]
{ /** The diagonal from vertex 2 to vertex 0. */
  def diag0: LSegLen2[VT]

  /** The diagonal from vertex 3 to vertex 1. */
  def diag1: LSegLen2[VT]

  /** The X component of the centre of the quadrilateral, defined as where the diagonals cross. */
  def cenX: Length

  /** The Y component of the centre of the quadrilateral, defined as where the diagonals cross. */
  def cenY: Length


  def diagTriangle0: TriangleLen2[VT]
}

trait QuadM2 extends QuadLen2[PtM2], PolygonM2P4
{ override def diag0: LSegM2 = LSegM2(v2xMNum, v2yMNum, v0xMNum, v0yMNum)
  override def diag1: LSegM2 = LSegM2(v3xMNum, v3yMNum, v1xMNum, v1yMNum)
  def cenXMNum: Double = (v0xMNum + v1xMNum + v2xMNum + v3xMNum) / 4
  override def cenX: Metres = Metres(cenXMNum)
  def cenYMNum: Double = (v0yMNum + v1yMNum + v2yMNum + v3yMNum) / 4
  override def cenY: Metres = Metres(cenYMNum)
  override def diagTriangle0: TriangleM2 = TriangleM2(v0xMNum, v0yMNum, cenXMNum, cenYMNum, v3xMNum, v3yMNum)
}