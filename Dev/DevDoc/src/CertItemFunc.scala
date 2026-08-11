/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, osweb.*, wcode.*, Colour.LightGreen

def CertItemFunc: (OptionHtml, OptionHtml, String, String) => RArr[XCon] = (bound, opSys, uName, domain) => {
  val mainElems: RArr[XCon] = bound match{
    case PublicInternet => RArr(
      "Install snap",
      BashLine("sudo apt install snapd"),
      "Install certbot",
      BashLine("sudo snap install --classic certbot"),
      CodeOutputLine("certbot 5.1.0 from Certbot Project (certbot-eff✓) installed"),
      "Ensure that the cerbot command can be run",
      BashLine("sudo ln -s /snap/bin/certbot /usr/bin/certbot"),
      "Stop tomcat.",
      BashLine("sudo systemctl stop tom11"),
      "Install certificate. When asked to enter domain name, you can enter multiple web domains, but you only use the first in the ensuing commands.",
      BashLine("sudo certbot certonly --standalone"),
      "Configure permissions to certificates",
      BashLine(s"sudo chgrp -R $uName /etc/letsencrypt/live/"),
      BashLine(s"sudo chgrp -R $uName /etc/letsencrypt/archive/"),
      BashLine("sudo chmod -R 750 /etc/letsencrypt/live/"),
      BashLine("sudo chmod -R 750 /etc/letsencrypt/archive/"),
      BashLine(s"sudo chmod 640 /etc/letsencrypt/live/$domain/privkey.pem"),
      BashLine(s"sudo chmod 644 /etc/letsencrypt/live/$domain/cert.pem"),
      BashLine(s"sudo chmod 644 /etc/letsencrypt/live/$domain/chain.pem"),
      "Check permissions - if you dont have access then something wrong...",
      BashLine(s"ls -la /etc/letsencrypt/live/$domain/"),
    )
    case LocalHost if opSys.isInstanceOf[LinuxSystem] =>
      val start: RArr[XCon] = opSys match{
        case UbuntuDeriv => RArr(
          BashLine("sudo apt install libnss3-tools"),
          BashLine("sudo apt install mkcert"),
          BashLine("mkcert -install"),
        )
        case ArchDeriv => RArr(
          BashLine("sudo pacman -S nss"),
          BashLine("sudo pacman -S mkcert")
        )
        case _ => RArr(DivHtml("No code for the installation of libnss3-tools and mkcert"))
      }
      start ++ RArr(
      BashLine("mkcert localhost 127.0.0.1 ::1"),
      PreCode(
      """Created a new certificate valid for the following names:
      |- "localhost"
      |- "127.0.0.1"
      |- "::1"
      |
      |The certificate is at "./localhost+2.pem" and the key at "./test.example.com+3-key.pem" ✅
      |
      |It will expire on 23 January 2024 🗓""".stripMargin, PinkStyleAtt),
      BashLine("sudo cp ./localhost+2* /etc/ssl")
    )
    case _ => RArr("No code for this combination.")
  }

  DivHtml("SSL Certification".bHtml) %: mainElems
}