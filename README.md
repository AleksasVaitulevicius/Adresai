#Adresu registras.
#v.1 Paprastas adresu sarasas. Kiekvienam adresui suteikiamas ID, pagal kuri adresus galima istrinti, surasti.
    Galimos komandos: GetAll - grazina visus adresus
                      AddModel - prideda nauja modeli
                      GetModel - grazina adresa pagal nurodyta ID
                      DeleteModel - istrina adresa pagal nurodyta ID

    paleidimui paleisti komandas:
      docker build -t laju2259/addresses .
      docker run -d -p 8080:8080 laju2259/addresses:1
