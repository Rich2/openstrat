/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** The essential persistence type class. Tt implements both a ShowT style type class interface, the production of a String representation of the
 *  value but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object
 *  in the Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends ShowT[T] with UnShow[T]