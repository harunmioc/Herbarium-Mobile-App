package com.example.myapplication

val biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljaniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljaniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljaniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljaniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljaniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Peršun (Petroselinum crispum)",
        porodica = "Apiaceae (štitarke)",
        medicinskoUpozorenje = "Osobe koje su alergične na peršun trebaju izbjegavati konzumaciju i kontakt s ovim biljnim proizvodom radi sprječavanja mogućih alergijskih reakcija.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata", "Juha/Supa", "Besamel umak", "Odrezak"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
        zemljaniTipovi =listOf(Zemljiste.ILOVACA, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Matičnjak (Melissa officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Ako uzimate sedative (za nesanicu ili anksioznost) ili lijekove za regulaciju štitne žlijezde, posavjetujte se s liječnikom prije uzimanja matičnjaka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Salata", "Juha/Supa", "Besamel umak", "Odrezak"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
        zemljaniTipovi =listOf(Zemljiste.ILOVACA, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kurkuma (Curcuma longa)",
        porodica = "Zingiberaceae (đumbirovke)",
        medicinskoUpozorenje = "Trebali biste biti svjesni rizika od ozljede jetre u rijetkim slučajevima. Iako je ozljeda jetre rijedak neželjeni događaj, može biti ozbiljna.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Curry", "Juha/Supa", "Marinirana piletina s kurkumom i limunom", "Riža"),
        klimatskiTipovi = listOf(KlimatskiTip.TROPSKA),
        zemljaniTipovi =listOf(Zemljiste.ILOVACA, Zemljiste.GLINENO)
    ),
    Biljka(
        naziv = "Majčina dušica (Thymus serpyllum)",
        porodica = " Lamiaceae (usnate)",
        medicinskoUpozorenje = " Majčina dušica je sigurna za upotrebu.",
        medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU,MedicinskaKorist.SMIRENJE),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Pasta","Lazanja","Pizza"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljaniTipovi = listOf(Zemljiste.PJESKOVITO)
    ),
    Biljka(
        naziv = "Origano (Origanum vulgare)",
        porodica = " Lamiaceae (usnate)",
        medicinskoUpozorenje = " Origano je obično siguran za upotrebu.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE,MedicinskaKorist.SMIRENJE),
        profilOkusa = ProfilOkusaBiljke.LJUTO,
        jela = listOf("Pizza","Lazanja","Lazanja"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljaniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    )
)




