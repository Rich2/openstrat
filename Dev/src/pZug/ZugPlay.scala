/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug

case class ZPlayer(polities: Refs[Polity])

object PlayBritain extends ZPlayer (Refs(Britain))
object PlayGermany extends ZPlayer (Refs(Germany))
object PlayGermanyBritain extends ZPlayer (Refs(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (Refs(Germany, France))