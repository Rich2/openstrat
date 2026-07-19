/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait QuadLen2[+VT <: PtLen2] extends PolygonLen2P4[VT]
{ /** The diagonal from vertex 2 to vertex 0. */
  def diag0: LSegLen2[VT]

  /** The diagonal from vertex 3 to vertex 1. */
  def diag1: LSegLen2[VT]

  def diags: LineSegLen2Arr[VT]

  /** The X component of the centre of the quadrilateral, defined as where the diagonals cross. */
  def cenX: Length

  /** The Y component of the centre of the quadrilateral, defined as where the diagonals cross. */
  def cenY: Length

  /** The triangle defined vertex 0, the centre and vertex 3. */
  def diagTriangle0: TriangleLen2[VT]

  /** The triangle defined vertex 1, the centre and vertex 0. */
  def diagTriangle1: TriangleLen2[VT]

  /** The triangle defined vertex 2, the centre and vertex 2. */
  def diagTriangle2: TriangleLen2[VT]

  /** The triangle defined vertex 3, the centre and vertex 2. */
  def diagTriangle3: TriangleLen2[VT]
}

trait QuadM2 extends QuadLen2[PtM2], PolygonM2P4
{ override def diag0: LSegM2 = LSegM2(v2xMNum, v2yMNum, v0xMNum, v0yMNum)
  override def diag1: LSegM2 = LSegM2(v3xMNum, v3yMNum, v1xMNum, v1yMNum)
  def cenXMNum: Double = (v0xMNum + v1xMNum + v2xMNum + v3xMNum) / 4
  override def cenX: Metres = Metres(cenXMNum)
  def cenYMNum: Double = (v0yMNum + v1yMNum + v2yMNum + v3yMNum) / 4
  override def cenY: Metres = Metres(cenYMNum)
  override def diagTriangle0: TriangleM2 = TriangleM2(v0xMNum, v0yMNum, cenXMNum, cenYMNum, v3xMNum, v3yMNum)
  override def diagTriangle1: TriangleM2 = TriangleM2(v1xMNum, v1yMNum, cenXMNum, cenYMNum, v0xMNum, v0yMNum)
  override def diagTriangle2: TriangleM2 = TriangleM2(v2xMNum, v2yMNum, cenXMNum, cenYMNum, v1xMNum, v1yMNum)
  override def diagTriangle3: TriangleM2 = TriangleM2(v3xMNum, v3yMNum, cenXMNum, cenYMNum, v2xMNum, v2yMNum)
}

/** 2-dimensional graphic based ona quadrilateral */
trait QuadLen2Graphic extends PolygonLen2Graphic
{ override def shape: QuadLen2[PtLen2]
}