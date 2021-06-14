import React, { useEffect, useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ProfileDiv,
  StyledHeaderDiv,
  StyledLeftLayout,
} from '../components/Styled';
import SideMenu from '../components/SideMenu';
import PostList from '../components/Post/PostList';
import { GET_LIKE_DONE, GET_LIKE_REQUEST } from '../../reducers/like';
import TagShower from '../components/tag/TagShower';

export default function LikeList() {
  const [taglist, setTaglist] = useState([]);
  const targetRef = useRef();
  const tagss = useSelector((state) => state.tag);
  const like = useSelector((state) => state.like);
  useEffect(() => {
    if (tagss.tagGetData) {
      const temp = Array.from(tagss.tagGetData.tags.POSITION);
      const tt = temp.map((t) => {
        return { ...t, selected: false };
      });
      setTaglist((p) => [...p, ...tt]);
    }
  }, [tagss.tagGetData]);

  return (
    <>
      <div className={'container text-left'}>
        <StyledHeaderDiv padding title={'게시글 찜목록'}></StyledHeaderDiv>
        <div className="container-fluid" style={{ marginTop: '70px' }}>
          <div className="row justify-content-center">
            <StyledLeftLayout
              borderNone
              className={'col-12 col-lg-2 text-left'}
            >
              <SideMenu />
            </StyledLeftLayout>
            {/* 태그 end*/}

            {/* 게시글*/}
            <ProfileDiv className={'col-12 col-lg-10'}>
              <TagShower tagList={taglist} setTagList={setTaglist}></TagShower>
              <PostList
                type={'community'}
                targetRef={targetRef}
                listLoading={like.getLikeLoading}
                done={like.getLikeDone}
                list={like.list}
                typeRequest={GET_LIKE_REQUEST}
                typeDone={GET_LIKE_DONE}
              ></PostList>
              <div ref={targetRef}></div>
            </ProfileDiv>
            {/* 게시글 end*/}
          </div>
        </div>
      </div>
    </>
  );
}
