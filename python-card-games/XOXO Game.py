from d4Lib import*
from tkinter import *

#creation d'un tableau vide
tab=[[1,2,3],[4,5,6],[7,8,9]]
effaceTableau (tab)
I=1
x=150
FEN=Tk()
can=Canvas(FEN,width=x*3,height=x*3,bg='orange')
can.pack(side=TOP,padx=5,pady=5)

#creation du tableau sous forme de damier
def tableauJeu():
    b = 0   
    while b < 3:  
        if b % 2 == 0: 
            a = 0               
        else:                    
            a = 1              
        lignesT(a*x, b*x)     
        b=b+1
def lignesT(a, b):    
    i = 0  
    while i < 2 :   
        can.create_rectangle(a, b, a+x, b+x, fill='red') 
        i += 1
        if a==x:
            break
        a += x*2
        
#creation des pions
def O(a,b,R,couleur):
    '''
    dessine le pion O
    '''
    can.create_oval(a-R, b-R, a+R, b+R, fill=couleur)
def croix(a,b):
    '''
    dessine le pion croix
    '''
    can.create_line(a,b,a+x,b+x)
    can.create_line(a+x,b,a,b+x)
        
def detecteClic(clic):
    global A
    global B
    global I
    global res
    A=int(clic.x)
    B=int(clic.y)
    if I==0:
         O((A//x)*x+(x/2),(B//x)*x+(x/2),(x/3),'orange')
         tab[A//x][B//x]='O'
         I=1
    else :
         croix((A//x)*x,(B//x)*x)
         tab[A//x][B//x]='X'
         I=0
    res=verifieGagner(tab)
    chaine.configure(text="clic détecté A="+str(clic.x)+" B="+str(clic.y))
    if res==True:
        chaine.configure(text="Fin du jeu,\n  appuyer 'QUITTER' \n pour quitter le jeu")
       

bouton1=Button(FEN, text="COMMENCER",command=tableauJeu)
bouton2=Button (FEN,text="QUITTER",command=FEN.destroy) 
bouton1.pack(side=RIGHT,padx=3,pady=3)
bouton2.pack(side=RIGHT,padx=3,pady=3)
can.bind("<Button-1>",detecteClic)
can.pack()
chaine=Label(FEN)
chaine.configure(text="Appuyer 'COMMENCER' \n pour commencer")
chaine.pack()

FEN.mainloop()

