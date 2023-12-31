<?php

namespace App\Controller;


use App\Entity\Post;
use App\Entity\Comment;
use App\Entity\User;
use App\Form\CommentType;
use App\Repository\CommentRepository;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/comment")
 */
class CommentController extends AbstractController
{
    /**
     * @Route("/backcommentmobile", name="appshowcommentmobile")
     */
    public function mobilecommentfind(CommentRepository $commentRepository, Request $request, NormalizerInterface $Normalizer): Response
    {
        $repository = $this->getdoctrine()->getRepository(Comment::class);
        $comment = $repository->findAll();
        $jsonContent = $Normalizer->normalize($comment, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
    }



    /*
    /**
     * @param CommentRepository $Repository
     * @return Response
     * @Route ("/statss", name="stat")


    public function statistiques(CommentRepository $commentRepository){
        $comment = $commentRepository->countByResp();
        $resp = [];
        $commentsCount = [];
        foreach($comment as $comments){

            $resp [] = $comments['resp'];
            $commentsCount[] = $comments['count'];
        }
        return $this->render('comment/stats.html.twig', [
            'resp' => $resp,
            'commentsCount' => $commentsCount
        ]);
    }
    */
public $id;
    /**
     * @Route("/statistique", name="app_comment_statistique")
     */
    public function stat(){

        $repository = $this->getDoctrine()->getRepository(Comment::class);
        $Comment = $repository->findAll();

        $em = $this->getDoctrine()->getManager();


        $pr1=0;
        $pr2=0;



        foreach ($Comment as $Comment)
        {
            if ( $Comment->getresp()=="5")  :

                $pr1+=1;
            else:

                $pr2+=1;


            endif;

        }

        $pieChart = new PieChart();
        $pieChart->getData()->setArrayToDataTable(
            [['resp', 'label'],
                ['5', $pr1],
                ['<5', $pr2],
            ]
        );
        $pieChart->getOptions()->setTitle('STATISTIQUE DU MEILLEUR POST SELON RATE');
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(900);
        $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
        $pieChart->getOptions()->getTitleTextStyle()->setColor('#91b59f');
        $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
        $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
        $pieChart->getOptions()->getTitleTextStyle()->setFontSize(20);

        return $this->render('comment/stats.html.twig', array('piechart' => $pieChart));
    }



    /**
     * @Route("/b", name="app_comment_index_back", methods={"GET"})
     */
    public function indexback(CommentRepository $commentRepository): Response
    {
        return $this->render('comment/back.html.twig', [
            'comments' => $commentRepository->findBy([],['idc'=>'desc']),
        ]);
    }

    /**
     * @Route("/addcomment", name="app_comment_new", methods={"GET","POST"})
     */
    public function new(Request $request, CommentRepository $commentRepository, PostRepository $postRepository): Response
    {
        $comment = new Comment();

        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commentRepository->add($comment);
            return $this->redirectToRoute('app_post_indexFront',[], Response::HTTP_SEE_OTHER);


        }

        return $this->render('comment/new.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/", name="app_comment_indexback", methods={"GET"})
*/
    public function index(Request $request ,CommentRepository $commentRepository): Response
    {

        return $this->render('comment/index.html.twig', [
            'comments' => $commentRepository->findBy([],['idc'=>'desc']),
        ]);
    }

    /**
     * @Route("/testcommment{id}", name="app_comment_index", methods={"GET"})
     */
    public function showback(Post $post,CommentRepository $commentRepository): Response
    {
        return $this->render('comment/index.html.twig', [
            'comments' => $commentRepository->findBy(['idPost'=>$post->getId()],['idc'=>'desc']),
        ]);
    }

    /**
     * @Route("/{idc}", name="app_comment_show", methods={"GET"})
     */
    public function show(Comment $comment): Response
    {
        return $this->render('comment/show.html.twig', [
            'comment' => $comment,
        ]);
    }


    /**
     * @Route("/{idc}/edit", name="app_comment_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Comment $comment, CommentRepository $commentRepository): Response
    {
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $commentRepository->add($comment);
            return $this->redirectToRoute('app_comment_indexback', [], Response::HTTP_SEE_OTHER);
        }
        return $this->render('comment/_form.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/backdeletetcomment{idc}", name="app_comment_deletebackcomment", methods={"GET","POST"})
     */
    public function deleteback(Request $request, Comment $comment, CommentRepository $commentRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$comment->getIdc(), $request->request->get('_token'))) {
            $commentRepository->remove($comment);
        }

        return $this->redirectToRoute('app_comment_index_back', [], Response::HTTP_SEE_OTHER);
    }

    /**
     * @Route("/{idc}", name="app_comment_delete", methods={"POST"})
     */
    public function delete(Request $request, Comment $comment, CommentRepository $commentRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$comment->getIdc(), $request->request->get('_token'))) {
            $commentRepository->remove($comment);
        }

        return $this->redirectToRoute('app_comment_indexback', [], Response::HTTP_SEE_OTHER);
    }


// codename one


    /**
     * @Route("/addCommentJSON/new",name="addCommentJSON")
     *
     *
     */
    public function addCommenttJSON(Request $request, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $comment = new Comment();
        $comment->setContenu($request->get('contenu'));
        $comment->setLabel($request->get('label'));
        $comment->setResp($request->get('resp'));
        $idPost=$request->get('idPost');
        $comment->setIdPost($this->getDoctrine()->getManager()-> getRepository(Post::class)->find($idPost));




        $em->persist($comment);
        $em->flush();
        $jsonContent = $Normalizer->normalize($comment, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));;

    }

    /**
     * @Route("/updateCommentJSON/{id}",name="updateCommentJSON")
     *
     *
     */
    public function updateCommenttJSON(Request $request, NormalizerInterface $Normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $comment = $em->getRepository(Comment::class)->find($id);
        $comment->setContenu($request->get('contenu'));
        $comment->setLabel($request->get('label'));
        $comment->setResp($request->get('resp'));
        $em->flush();
        $jsonContent = $Normalizer->normalize($comment, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));;

    }

    /**
     * @Route("/deleteCommentJSON/{id}",name="deletecommentJSON")
     *
     *
     */
    public function deleteCommtJSON(Request $request,NormalizerInterface $Normalizer,$id){
        $em=$this->getDoctrine()->getManager();
        $comment=$em->getRepository(Comment::class)->find($id);
        $em->remove($comment);
        $em->flush();
        $jsonContent=$Normalizer->normalize($comment, 'json',['groups'=>'post:read']);
        return new Response("Commment deleted successfully".json_encode($jsonContent));;


    }

}
