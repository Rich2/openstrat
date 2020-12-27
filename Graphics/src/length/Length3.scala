/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Length3 extends Any
{

}

/** 3 dimensional vector using metres as units rather than pure numbers. */
final class KMs3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends Length3// with ProdDbl3
