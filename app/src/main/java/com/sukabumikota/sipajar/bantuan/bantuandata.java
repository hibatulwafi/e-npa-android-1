package com.sukabumikota.sipajar.bantuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bantuandata {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> a = new ArrayList<String>();
        a.add("1. Pergi ke menu Laporan Pajak");
        a.add("2. Pilih Tipe Upload");
        a.add("3. Klik Gambar Untuk ambil Foto");
        a.add("4. Tekan UPLOAD FOTO");

        List<String> b = new ArrayList<String>();
        b.add("1. Anda Dapat Menunggu Persetujuan Admin");
        b.add("2. Kirim Pesan kepada admin jika diperlukan");
        b.add("3. Upload Foto Kembali");

        List<String> c = new ArrayList<String>();
        c.add("1. Pastikan Foto Sudah Jelas");
        c.add("2. Pastikan Tempat ambil foto sesuai, degan yang di daftarkan");
        c.add("3. Upload ulang foto");

        List<String> d = new ArrayList<String>();
        d.add("1. Pergi ke menu Lap. Pajak untuk melakukan Pelaporan");
        d.add("2. Pilih titik sumur yang akan di laporkan");
        d.add("3. Upload Foto Meteran untuk pelaporan");

        List<String> e = new ArrayList<String>();
        e.add("1. Anda bisa kunjungi kantor pajak untuk perizinan");
        e.add("2. Data Titik Sumur baru akan ditambah pada menu titik sumur");

        List<String> f = new ArrayList<String>();
        f.add("1. Silahkan kunjungi kantor untuk reset password");
        f.add("2. Anda bisa mengganti password pada menu Profile");
        f.add("3. Di Saran kan update password secara berkala");

        List<String> g = new ArrayList<String>();
        g.add("1. Kemungkinan Aplikasi sedang masa pemeliharaan");
        g.add("2. Tunggu beberapa saat, atau bisa chat admin pada menu pesan");
        g.add("3. Itu bukan masalah besar, data data anda akan aman");

        expandableListDetail.put("Bagaimana Cara Upload Foto?", a);
        expandableListDetail.put("Bagaimana Jika foto yang saya masukan salah?", b);
        expandableListDetail.put("Bagaimana Jika Laporan ditolak?", c);
        expandableListDetail.put("Bagaimana Cara Penggunaan Aplikasi?", d);
        expandableListDetail.put("Bagaimana Cara Menambah Titik Sumur Baru?", e);
        expandableListDetail.put("Bagaimana Jika Password saya lupa?", f);
        expandableListDetail.put("Saya melihat Halaman Maintance?", g);
        return expandableListDetail;
    }
}
