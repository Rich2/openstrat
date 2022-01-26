/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[Polygon]] with at least 3 vertices. The PolygonNPlus traits include values for the vertices and the x and y components of the vertices. The X
 * and Y components are included because Graphics implementation APIs use them. */
trait Polygon3Plus extends Polygon
{ /** The X component of the v1 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or
      immediately clockwise from 12 o'clock. */
  def v1x: Double

  /** The Y component of the v1 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or
   * immediately clockwise from 12 o'clock. */
  def v1y: Double

  /** The v1 Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or immediately clockwise
   *  from 12 o'clock. */
  def v1: Pt2

  /** The X component of the v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or
   * immediately clockwise from 12 o'clock. */
  def v2x: Double

  /** The Y component of the v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or
   * immediately clockwise from 12 o'clock. */
  def v2y: Double

  /** The v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or immediately clockwise
   * from 12 o'clock. */
  def v2: Pt2

  /** The last vertex. The default convention places this just anti clockwise of 12 oclock. */
  override def vLast: Pt2 = vert(vertsNum - 1)

  /** Polygon side 0 from vertex 0 to vertex 1. */
  def side0: LineSeg = LineSeg(v0, v1)

  /** Polygon side 1 from vertex 1 to vertex 2. */
  def side1: LineSeg = LineSeg(v1, v2)

  /** Polygon side 2 from vertex 2 to vertex 3. */
  def side2: LineSeg = LineSeg(v2, ife(vertsNum == 3, v0, vert(3)))

  /** The centre or half way point of side 0 of this polygon. Side 0 starts at the vertex v0 and ends at the vertex v1. This can be thought of as
   *  vertex 0.5. */
  def sd0Cen: Pt2
  /** The X component of the centre or half way point of side 0 of this polygon. */
  def sd0CenX: Double
  /** The Y component of the centre or half way point of side 0 of this polygon. */
  def sd0CenY: Double

  /** The centre or half way point of side 1 of this polygon. Side 1 starts at the v1 vertex and ends at the v2 vertex. This can be thought of as
   * vertex 1.5 */
  def sd1Cen: Pt2
  /** The X component of the centre or half way point of side 1 of this polygon. */
  def sd1CenX: Double
  /** The Y component of the centre or half way point of side 1 of this polygon. */
  def sd1CenY: Double

  /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v2 vertex and ends at the v3 vertex. This can be thought of as
   *  vertex 2.5. */
  def sd2Cen: Pt2
  /** The X component of the centre or half way point of side 2 of this polygon. */
  def sd2CenX: Double
  /** The Y component of the centre or half way point of side 2 of this polygon. */
  def sd2CenY: Double
}

/** A [[Polygon]] with at least 4 vertices. */
trait Polygon4Plus extends Polygon3Plus
{ /** The X component of vertex 3. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 0 immediately
      clockwise from 12 o'clock. */
  def v3x: Double
  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v3y: Double
  /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v3: Pt2

  /** The centre or half way point of side 3 of this polygon. */
  def sd3Cen: Pt2
  /** The X component of the centre or half way point of side 3 of this polygon. */
  def sd3CenX: Double
  /** The Y component of the centre or half way point of side 3 of this polygon. */
  def sd3CenY: Double
}

/** A [[Polygon]] with at least 5 vertices. */
trait Polygon5Plus extends Polygon4Plus
{ /** Vertex 4. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 0 immediately clockwise from 12
   *  o'clock. */
  def v4: Pt2
  /** The X component of the vertex 4. */
  def v4x: Double
  /** The Y component of the vertex 4. */
  def v4y: Double


  /** The centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be thought of as
   * vertex 4.5. */
  def sd4Cen: Pt2
  /** The X component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  def sd4CenX: Double
  /** The Y component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  def sd4CenY: Double
}

/** A [[Polygon]] with at least 6 vertices. */
trait Polygon6Plus extends Polygon5Plus
{ /** Vertex 5. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 0 immediately clockwise from 12
   *  o'clock. */
  def v5: Pt2
  /** The X component of the 6th Vertex. */
  def v5x: Double
  /** The Y component of the 6th Vertex. */
  def y5: Double

  /** The centre or half way point of side 5 of this polygon. Side 5 starts at the v5 vertex and ends at the v6 vertex. This can be thought of as
   * vertex 5.5. */
  def sd5Cen: Pt2
  /** The X component of the centre or half way point of side 6 of this polygon. */
  def sd5CenX: Double
  /** The Y component of the centre or half way point of side 5 of this polygon. */
  def sd5CenY: Double
}