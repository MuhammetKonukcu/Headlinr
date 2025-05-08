package com.muhammetkonukcu.headlinr.util

import androidx.compose.runtime.Composable
import com.muhammetkonukcu.headlinr.model.FlagModel
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.ae_flag
import headlinr.composeapp.generated.resources.ar_flag
import headlinr.composeapp.generated.resources.at_flag
import headlinr.composeapp.generated.resources.au_flag
import headlinr.composeapp.generated.resources.be_flag
import headlinr.composeapp.generated.resources.bg_flag
import headlinr.composeapp.generated.resources.br_flag
import headlinr.composeapp.generated.resources.ca_flag
import headlinr.composeapp.generated.resources.ch_flag
import headlinr.composeapp.generated.resources.cn_flag
import headlinr.composeapp.generated.resources.co_flag
import headlinr.composeapp.generated.resources.country_argentina
import headlinr.composeapp.generated.resources.country_australia
import headlinr.composeapp.generated.resources.country_austria
import headlinr.composeapp.generated.resources.country_belgium
import headlinr.composeapp.generated.resources.country_brazil
import headlinr.composeapp.generated.resources.country_bulgaria
import headlinr.composeapp.generated.resources.country_canada
import headlinr.composeapp.generated.resources.country_china
import headlinr.composeapp.generated.resources.country_colombia
import headlinr.composeapp.generated.resources.country_czech_republic
import headlinr.composeapp.generated.resources.country_egypt
import headlinr.composeapp.generated.resources.country_france
import headlinr.composeapp.generated.resources.country_germany
import headlinr.composeapp.generated.resources.country_greece
import headlinr.composeapp.generated.resources.country_hong_kong
import headlinr.composeapp.generated.resources.country_hungary
import headlinr.composeapp.generated.resources.country_india
import headlinr.composeapp.generated.resources.country_indonesia
import headlinr.composeapp.generated.resources.country_ireland
import headlinr.composeapp.generated.resources.country_israel
import headlinr.composeapp.generated.resources.country_italy
import headlinr.composeapp.generated.resources.country_japan
import headlinr.composeapp.generated.resources.country_latvia
import headlinr.composeapp.generated.resources.country_lithuania
import headlinr.composeapp.generated.resources.country_malaysia
import headlinr.composeapp.generated.resources.country_mexico
import headlinr.composeapp.generated.resources.country_morocco
import headlinr.composeapp.generated.resources.country_netherlands
import headlinr.composeapp.generated.resources.country_new_zealand
import headlinr.composeapp.generated.resources.country_nigeria
import headlinr.composeapp.generated.resources.country_norway
import headlinr.composeapp.generated.resources.country_philippines
import headlinr.composeapp.generated.resources.country_poland
import headlinr.composeapp.generated.resources.country_portugal
import headlinr.composeapp.generated.resources.country_romania
import headlinr.composeapp.generated.resources.country_saudi_arabia
import headlinr.composeapp.generated.resources.country_serbia
import headlinr.composeapp.generated.resources.country_singapore
import headlinr.composeapp.generated.resources.country_slovakia
import headlinr.composeapp.generated.resources.country_slovenia
import headlinr.composeapp.generated.resources.country_south_africa
import headlinr.composeapp.generated.resources.country_south_korea
import headlinr.composeapp.generated.resources.country_sweden
import headlinr.composeapp.generated.resources.country_switzerland
import headlinr.composeapp.generated.resources.country_taiwan
import headlinr.composeapp.generated.resources.country_thailand
import headlinr.composeapp.generated.resources.country_turkey
import headlinr.composeapp.generated.resources.country_ukraine
import headlinr.composeapp.generated.resources.country_united_arab_emirates
import headlinr.composeapp.generated.resources.country_united_kingdom
import headlinr.composeapp.generated.resources.country_united_states
import headlinr.composeapp.generated.resources.country_venezuela
import headlinr.composeapp.generated.resources.cz_flag
import headlinr.composeapp.generated.resources.de_flag
import headlinr.composeapp.generated.resources.eg_flag
import headlinr.composeapp.generated.resources.fr_flag
import headlinr.composeapp.generated.resources.gb_flag
import headlinr.composeapp.generated.resources.gr_flag
import headlinr.composeapp.generated.resources.hk_flag
import headlinr.composeapp.generated.resources.hu_flag
import headlinr.composeapp.generated.resources.id_flag
import headlinr.composeapp.generated.resources.ie_flag
import headlinr.composeapp.generated.resources.il_flag
import headlinr.composeapp.generated.resources.in_flag
import headlinr.composeapp.generated.resources.it_flag
import headlinr.composeapp.generated.resources.jp_flag
import headlinr.composeapp.generated.resources.kr_flag
import headlinr.composeapp.generated.resources.lt_flag
import headlinr.composeapp.generated.resources.lv_flag
import headlinr.composeapp.generated.resources.ma_flag
import headlinr.composeapp.generated.resources.mx_flag
import headlinr.composeapp.generated.resources.my_flag
import headlinr.composeapp.generated.resources.ng_flag
import headlinr.composeapp.generated.resources.nl_flag
import headlinr.composeapp.generated.resources.no_flag
import headlinr.composeapp.generated.resources.nz_flag
import headlinr.composeapp.generated.resources.ph_flag
import headlinr.composeapp.generated.resources.pl_flag
import headlinr.composeapp.generated.resources.pt_flag
import headlinr.composeapp.generated.resources.ro_flag
import headlinr.composeapp.generated.resources.rs_flag
import headlinr.composeapp.generated.resources.sa_flag
import headlinr.composeapp.generated.resources.se_flag
import headlinr.composeapp.generated.resources.sg_flag
import headlinr.composeapp.generated.resources.si_flag
import headlinr.composeapp.generated.resources.sk_flag
import headlinr.composeapp.generated.resources.th_flag
import headlinr.composeapp.generated.resources.tr_flag
import headlinr.composeapp.generated.resources.tw_flag
import headlinr.composeapp.generated.resources.ua_flag
import headlinr.composeapp.generated.resources.us_flag
import headlinr.composeapp.generated.resources.ve_flag
import headlinr.composeapp.generated.resources.za_flag

@Composable
fun GetFlags(): List<FlagModel> {
    return listOf(
        FlagModel(
            countryCode = "ar",
            imageRes = Res.drawable.ar_flag,
            countryName = Res.string.country_argentina
        ),
        FlagModel(
            countryCode = "au",
            imageRes = Res.drawable.au_flag,
            countryName = Res.string.country_australia
        ),
        FlagModel(
            countryCode = "at",
            imageRes = Res.drawable.at_flag,
            countryName = Res.string.country_austria
        ),
        FlagModel(
            countryCode = "be",
            imageRes = Res.drawable.be_flag,
            countryName = Res.string.country_belgium
        ),
        FlagModel(
            countryCode = "br",
            imageRes = Res.drawable.br_flag,
            countryName = Res.string.country_brazil
        ),
        FlagModel(
            countryCode = "bg",
            imageRes = Res.drawable.bg_flag,
            countryName = Res.string.country_bulgaria
        ),
        FlagModel(
            countryCode = "ca",
            imageRes = Res.drawable.ca_flag,
            countryName = Res.string.country_canada
        ),
        FlagModel(
            countryCode = "cn",
            imageRes = Res.drawable.cn_flag,
            countryName = Res.string.country_china
        ),
        FlagModel(
            countryCode = "co",
            imageRes = Res.drawable.co_flag,
            countryName = Res.string.country_colombia
        ),
        FlagModel(
            countryCode = "cz",
            imageRes = Res.drawable.cz_flag,
            countryName = Res.string.country_czech_republic
        ),
        FlagModel(
            countryCode = "eg",
            imageRes = Res.drawable.eg_flag,
            countryName = Res.string.country_egypt
        ),
        FlagModel(
            countryCode = "fr",
            imageRes = Res.drawable.fr_flag,
            countryName = Res.string.country_france
        ),
        FlagModel(
            countryCode = "de",
            imageRes = Res.drawable.de_flag,
            countryName = Res.string.country_germany
        ),
        FlagModel(
            countryCode = "gr",
            imageRes = Res.drawable.gr_flag,
            countryName = Res.string.country_greece
        ),
        FlagModel(
            countryCode = "hk",
            imageRes = Res.drawable.hk_flag,
            countryName = Res.string.country_hong_kong
        ),
        FlagModel(
            countryCode = "hu",
            imageRes = Res.drawable.hu_flag,
            countryName = Res.string.country_hungary
        ),
        FlagModel(
            countryCode = "in",
            imageRes = Res.drawable.in_flag,
            countryName = Res.string.country_india
        ),
        FlagModel(
            countryCode = "id",
            imageRes = Res.drawable.id_flag,
            countryName = Res.string.country_indonesia
        ),
        FlagModel(
            countryCode = "ie",
            imageRes = Res.drawable.ie_flag,
            countryName = Res.string.country_ireland
        ),
        FlagModel(
            countryCode = "il",
            imageRes = Res.drawable.il_flag,
            countryName = Res.string.country_israel
        ),
        FlagModel(
            countryCode = "it",
            imageRes = Res.drawable.it_flag,
            countryName = Res.string.country_italy
        ),
        FlagModel(
            countryCode = "jp",
            imageRes = Res.drawable.jp_flag,
            countryName = Res.string.country_japan
        ),
        FlagModel(
            countryCode = "lv",
            imageRes = Res.drawable.lv_flag,
            countryName = Res.string.country_latvia
        ),
        FlagModel(
            countryCode = "lt",
            imageRes = Res.drawable.lt_flag,
            countryName = Res.string.country_lithuania
        ),
        FlagModel(
            countryCode = "my",
            imageRes = Res.drawable.my_flag,
            countryName = Res.string.country_malaysia
        ),
        FlagModel(
            countryCode = "mx",
            imageRes = Res.drawable.mx_flag,
            countryName = Res.string.country_mexico
        ),
        FlagModel(
            countryCode = "ma",
            imageRes = Res.drawable.ma_flag,
            countryName = Res.string.country_morocco
        ),
        FlagModel(
            countryCode = "nl",
            imageRes = Res.drawable.nl_flag,
            countryName = Res.string.country_netherlands
        ),
        FlagModel(
            countryCode = "nz",
            imageRes = Res.drawable.nz_flag,
            countryName = Res.string.country_new_zealand
        ),
        FlagModel(
            countryCode = "ng",
            imageRes = Res.drawable.ng_flag,
            countryName = Res.string.country_nigeria
        ),
        FlagModel(
            countryCode = "no",
            imageRes = Res.drawable.no_flag,
            countryName = Res.string.country_norway
        ),
        FlagModel(
            countryCode = "ph",
            imageRes = Res.drawable.ph_flag,
            countryName = Res.string.country_philippines
        ),
        FlagModel(
            countryCode = "pl",
            imageRes = Res.drawable.pl_flag,
            countryName = Res.string.country_poland
        ),
        FlagModel(
            countryCode = "pt",
            imageRes = Res.drawable.pt_flag,
            countryName = Res.string.country_portugal
        ),
        FlagModel(
            countryCode = "ro",
            imageRes = Res.drawable.ro_flag,
            countryName = Res.string.country_romania
        ),
        FlagModel(
            countryCode = "sa",
            imageRes = Res.drawable.sa_flag,
            countryName = Res.string.country_saudi_arabia
        ),
        FlagModel(
            countryCode = "rs",
            imageRes = Res.drawable.rs_flag,
            countryName = Res.string.country_serbia
        ),
        FlagModel(
            countryCode = "sg",
            imageRes = Res.drawable.sg_flag,
            countryName = Res.string.country_singapore
        ),
        FlagModel(
            countryCode = "sk",
            imageRes = Res.drawable.sk_flag,
            countryName = Res.string.country_slovakia
        ),
        FlagModel(
            countryCode = "si",
            imageRes = Res.drawable.si_flag,
            countryName = Res.string.country_slovenia
        ),
        FlagModel(
            countryCode = "za",
            imageRes = Res.drawable.za_flag,
            countryName = Res.string.country_south_africa
        ),
        FlagModel(
            countryCode = "kr",
            imageRes = Res.drawable.kr_flag,
            countryName = Res.string.country_south_korea
        ),
        FlagModel(
            countryCode = "se",
            imageRes = Res.drawable.se_flag,
            countryName = Res.string.country_sweden
        ),
        FlagModel(
            countryCode = "ch",
            imageRes = Res.drawable.ch_flag,
            countryName = Res.string.country_switzerland
        ),
        FlagModel(
            countryCode = "tw",
            imageRes = Res.drawable.tw_flag,
            countryName = Res.string.country_taiwan
        ),
        FlagModel(
            countryCode = "th",
            imageRes = Res.drawable.th_flag,
            countryName = Res.string.country_thailand
        ),
        FlagModel(
            countryCode = "tr",
            imageRes = Res.drawable.tr_flag,
            countryName = Res.string.country_turkey
        ),
        FlagModel(
            countryCode = "ae",
            imageRes = Res.drawable.ae_flag,
            countryName = Res.string.country_united_arab_emirates
        ),
        FlagModel(
            countryCode = "ua",
            imageRes = Res.drawable.ua_flag,
            countryName = Res.string.country_ukraine
        ),
        FlagModel(
            countryCode = "gb",
            imageRes = Res.drawable.gb_flag,
            countryName = Res.string.country_united_kingdom
        ),
        FlagModel(
            countryCode = "us",
            imageRes = Res.drawable.us_flag,
            countryName = Res.string.country_united_states
        ),
        FlagModel(
            countryCode = "ve",
            imageRes = Res.drawable.ve_flag,
            countryName = Res.string.country_venezuela
        ),
    )
}

@Composable
fun getFlag(countryCode: String): FlagModel {
    return GetFlags().find { it.countryCode == countryCode }
        ?: GetFlags().find { it.countryCode == "tr" }!!
}