import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import ResumeWow from '../components/resume/ResumeWow';
import {
  ProfileDiv,
  StyledButton,
  StyledHeaderDiv,
  StyledLeftLayout,
} from '../components/Styled';
import SideMenu from '../components/SideMenu';
import { RESUME_LIST_DONE, RESUME_LIST_REQUEST } from '../../reducers/resume';

export default function ResumeList() {
  const dispatch = useDispatch();
  const page = useRef(0);
  const [, setLoading] = useState(false);
  const isLast = useRef(false);
  const [resumeList, setResumeList] = useState([]);
  const user = useSelector((state) => state.user);
  const resume = useSelector((state) => state.resume);

  useEffect(() => {
    if (user.me === null) return;
    dispatch({ type: RESUME_LIST_REQUEST, data: { size: 99, page: 0 } });
  }, [user.me, dispatch]);

  useEffect(() => {
    if (resume.resumeListLoading) {
      setLoading((prev) => true);
    }
    if (resume.resumeListDone) {
      setLoading((prev) => false);
      setResumeList((prev) => [...prev, ...resume.resumeList.content]);
      if (resume.resumeList.last) {
        isLast.current = true;
      } else {
        page.current++;
      }
      dispatch({ type: RESUME_LIST_DONE });
    }
  }, [dispatch, resume, setResumeList]);

  // useEffect(() => {
  //   if (resume.resumeList) {
  //     const tmp = resume.resumeList.content.length;
  //     setResumeCount(tmp);
  //     setArr(resume.resumeList.content);
  //     dispatch({ type: RESUME_LIST_DONE });
  //   }
  // }, [resume.resumeList, dispatch]);

  const mapResume = resumeList.map((rl) => (
    <div key={rl.id}>
      <ResumeWow
        id={rl.id}
        title={rl.title}
        open={rl.open}
        createdDate={
          rl.createdDate[0] + '/' + rl.createdDate[1] + '/' + rl.createdDate[2]
        }
        modifiedDate={
          rl.modifiedDate[0] +
          '/' +
          rl.modifiedDate[1] +
          '/' +
          rl.modifiedDate[2]
        }
      />
    </div>
  ));

  return (
    <>
      <div className="container text-left">
        <StyledHeaderDiv padding title={'이력서'}>
          <div style={{ flex: '0 0' }}>
            <Link to="/resume/new">
              <StyledButton wide>작성</StyledButton>
            </Link>
          </div>
        </StyledHeaderDiv>
        <div className="container" style={{ marginTop: '70px' }}>
          <div className="row justify-content-center">
            <StyledLeftLayout
              borderNone
              className={'col-12 col-lg-2 text-left'}
            >
              <SideMenu />
            </StyledLeftLayout>
            <ProfileDiv className={'col-12 col-lg-10 p-0'}>
              {mapResume}
            </ProfileDiv>
          </div>
        </div>
      </div>
    </>
  );
}
